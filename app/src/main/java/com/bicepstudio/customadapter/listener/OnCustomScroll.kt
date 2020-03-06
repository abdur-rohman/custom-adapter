package com.bicepstudio.customadapter.listener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class OnCustomScroll(layoutManager: RecyclerView.LayoutManager, loadMoreAt: Int = 3) : RecyclerView.OnScrollListener() {
    private var visibleThreshold = loadMoreAt
    private var currentPage = 0
    private var loading = true
    private val startingPageIndex = 0
    private var previousTotalItemCount = 0
    private var layoutManager: RecyclerView.LayoutManager

    init {
        when (layoutManager) {
            is GridLayoutManager, is StaggeredGridLayoutManager -> when (layoutManager) {
                is GridLayoutManager -> {
                    this.layoutManager = layoutManager
                    visibleThreshold *= layoutManager.spanCount
                }
                else -> {
                    this.layoutManager = layoutManager as StaggeredGridLayoutManager
                    visibleThreshold *= layoutManager.spanCount
                }
            }
            else -> this.layoutManager = layoutManager as LinearLayoutManager
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0

        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) maxSize = lastVisibleItemPositions[i]
            else if (lastVisibleItemPositions[i] > maxSize) maxSize = lastVisibleItemPositions[i]
        }

        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItem: Int = layoutManager.itemCount
        var lastVisible = 0

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastItems: IntArray = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisible = getLastVisibleItem(lastItems)
            }
            is LinearLayoutManager -> lastVisible = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            is GridLayoutManager -> lastVisible = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }

        if (totalItem < previousTotalItemCount) {
            currentPage = startingPageIndex

            previousTotalItemCount = totalItem

            if (totalItem == 0) loading = true
        }

        if (loading && totalItem > previousTotalItemCount) {
            loading = false

            previousTotalItemCount = totalItem
        }

        if (!loading && lastVisible + visibleThreshold > totalItem) {
            currentPage++

            onLoadMore(currentPage, totalItem)

            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
}