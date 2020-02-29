# Custom Adapter
> Custom adapter is a tool that makes it easy for programmers to make adapters on recylerview without having to create multiple classes

## Usage
###Data Class Menu

    data class Menu(val icon: Int, val name: String)

### Data Class Account

    data class Account(val username: String, val email: String)

### item_menu.xml

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tool="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        app:cardCornerRadius="8dp"
        app:contentPadding="8dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <ImageView
                android:id="@+id/ivMenu"
                android:layout_width="64dp"
                android:layout_height="64dp"
                android:layout_marginEnd="8dp"
                tool:src="@android:drawable/ic_delete"/>

            <TextView
                android:id="@+id/tvMenu"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:textSize="20sp"
                android:textStyle="bold"
                android:textColor="@android:color/black"
                tool:text="@string/app_name"/>

        </LinearLayout>

    </androidx.cardview.widget.CardView>

### item_account.xml

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tool="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        app:cardCornerRadius="8dp"
        app:contentPadding="8dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <TextView
                android:id="@+id/tvUsername"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textSize="20sp"
                android:textStyle="bold"
                android:textColor="@android:color/black"
                tool:text="@tools:sample/full_names"/>

            <TextView
                android:id="@+id/tvEmail"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                tool:text="@tools:sample/lorem"/>

        </LinearLayout>

    </androidx.cardview.widget.CardView>

### activity_main.xml

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"
        tools:context=".MainActivity">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rvMenu"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rvAccount"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

    </LinearLayout>

### MainActivity.kt

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