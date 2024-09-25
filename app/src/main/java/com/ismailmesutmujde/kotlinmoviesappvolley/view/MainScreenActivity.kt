package com.ismailmesutmujde.kotlinmoviesappvolley.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ismailmesutmujde.kotlinmoviesappvolley.R
import com.ismailmesutmujde.kotlinmoviesappvolley.adapter.CategoriesRecyclerViewAdapter
import com.ismailmesutmujde.kotlinmoviesappvolley.databinding.ActivityMainScreenBinding
import com.ismailmesutmujde.kotlinmoviesappvolley.model.Categories
import org.json.JSONObject

class MainScreenActivity : AppCompatActivity() {

    private lateinit var bindingMainScreen : ActivityMainScreenBinding

    private lateinit var categoryList:ArrayList<Categories>
    private lateinit var adapterCategory: CategoriesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainScreen = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = bindingMainScreen.root
        setContentView(view)

        bindingMainScreen.toolbarCategory.title = "Categories"
        setSupportActionBar(bindingMainScreen.toolbarCategory)

        bindingMainScreen.recyclerViewCategory.setHasFixedSize(true)
        bindingMainScreen.recyclerViewCategory.layoutManager = LinearLayoutManager(this)

        /*
        categoryList = ArrayList()

        val c1 = Categories(1,"Science Fiction")
        val c2 = Categories(2,"Drama")
        val c3 = Categories(3,"Action")
        val c4 = Categories(4,"Adventure")
        val c5 = Categories(5,"Fantastic")
        val c6 = Categories(6,"Romantic")
        val c7 = Categories(1,"Comedy")
        val c8 = Categories(2,"Thriller")
        val c9 = Categories(1,"Horror")
        val c10 = Categories(2,"War")

        categoryList.add(c1)
        categoryList.add(c2)
        categoryList.add(c3)
        categoryList.add(c4)
        categoryList.add(c5)
        categoryList.add(c6)
        categoryList.add(c7)
        categoryList.add(c8)
        categoryList.add(c9)
        categoryList.add(c10)

        adapterCategory = CategoriesRecyclerViewAdapter(this, categoryList)
        bindingMainScreen.recyclerViewCategory.adapter = adapterCategory
        */

        allCategories()

    }

    fun allCategories() {
        val url = "http://kasimadalan.pe.hu/filmler/tum_kategoriler.php"
        val request = StringRequest(Request.Method.GET, url, Response.Listener { response->
            try {
                categoryList = ArrayList()
                val jsonObject = JSONObject(response)
                val categories = jsonObject.getJSONArray("kategoriler")

                for (i in 0 until categories.length()) {
                    val c = categories.getJSONObject(i)
                    val category = Categories(c.getInt("kategori_id")
                        ,c.getString("kategori_ad"))
                    categoryList.add(category)
                }

                adapterCategory = CategoriesRecyclerViewAdapter(this@MainScreenActivity, categoryList)
                bindingMainScreen.recyclerViewCategory.adapter = adapterCategory

            } catch (e:Exception) {
                e.printStackTrace()
            }
        }, Response.ErrorListener {  })
        Volley.newRequestQueue(this@MainScreenActivity).add(request)
    }
}