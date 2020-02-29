package com.bicepstudio.customadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bicepstudio.customadapter.adapter.CustomAdapter
import com.bicepstudio.customadapter.model.Account
import com.bicepstudio.customadapter.model.Menu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_account.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

    class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val menus = arrayListOf(
                Menu(android.R.drawable.ic_delete, "Delete"),
                Menu(android.R.drawable.ic_input_add, "Add"),
                Menu(android.R.drawable.ic_menu_search, "Search")
            )

            val accounts = arrayListOf(
                Account("blablabla", "blablabla@mail.com"),
                Account("lalalalal", "lalalalal@mail.com"),
                Account("heiheihei", "heiheihei@mail.com")
            )

            rvMenu.apply {
                adapter = object : CustomAdapter<Menu>(R.layout.item_menu) {
                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        holder.itemView.ivMenu.setImageDrawable(getDrawable(this.list[position].icon))
                        holder.itemView.tvMenu.text = this.list[position].name
                    }
                }.also {
                    it.list = menus
                }
            }

            rvAccount.apply {
                adapter = object : CustomAdapter<Account>(R.layout.item_account) {
                    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        holder.itemView.tvUsername.text = this.list[position].username
                        holder.itemView.tvEmail.text = this.list[position].email
                    }
                }.also {
                    it.list = accounts
                }
            }
        }
    }
