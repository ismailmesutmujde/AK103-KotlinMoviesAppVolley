package com.ismailmesutmujde.kotlinmoviesappvolley.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.ismailmesutmujde.kotlinmoviesappvolley.R
import com.ismailmesutmujde.kotlinmoviesappvolley.adapter.CategoriesRecyclerViewAdapter
import com.ismailmesutmujde.kotlinmoviesappvolley.adapter.MoviesRecyclerViewAdapter
import com.ismailmesutmujde.kotlinmoviesappvolley.databinding.ActivityMoviesScreenBinding
import com.ismailmesutmujde.kotlinmoviesappvolley.model.Categories
import com.ismailmesutmujde.kotlinmoviesappvolley.model.Directors
import com.ismailmesutmujde.kotlinmoviesappvolley.model.Movies
import org.json.JSONObject

class MoviesScreenActivity : AppCompatActivity() {
    private lateinit var bindingMoviesScreen : ActivityMoviesScreenBinding

    private lateinit var moviesList:ArrayList<Movies>
    private lateinit var adapterMovies: MoviesRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMoviesScreen = ActivityMoviesScreenBinding.inflate(layoutInflater)
        val view = bindingMoviesScreen.root
        setContentView(view)

        val category = intent.getSerializableExtra("categoryObject") as Categories

        bindingMoviesScreen.toolbarMovies.title = "Movies : ${category.category_name}"
        setSupportActionBar(bindingMoviesScreen.toolbarMovies)

        bindingMoviesScreen.recyclerViewMovies.setHasFixedSize(true)
        bindingMoviesScreen.recyclerViewMovies.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        /*
        moviesList = ArrayList()

        val c1 = Categories(1,"Science Fiction")
        val c2 = Categories(2,"Drama")
        val c3 = Categories(3,"Action")

        val d1 = Directors(1,"Christopher Nolan")
        val d2 = Directors(2,"Quentin Tarantino")
        val d3 = Directors(3,"Roman Polanski")

        val m1 = Movies(1,"Interstellar",2014,"interstellar",c1,d1)
        val m2 = Movies(2,"Django",2012,"django",c2,d2)
        val m3 = Movies(3,"Inception",2010,"inception",c3,d1)
        val m4 = Movies(4,"The Pianist",2002,"thepianist",c2,d3)

        moviesList.add(m1)
        moviesList.add(m2)
        moviesList.add(m3)
        moviesList.add(m4)

        adapterMovies = MoviesRecyclerViewAdapter(this, moviesList)
        bindingMoviesScreen.recyclerViewMovies.adapter = adapterMovies
        */

        allMoviesByCategoryId(category.category_id)
    }

    fun allMoviesByCategoryId(category_id:Int) {
        val url = "http://kasimadalan.pe.hu/filmler/filmler_by_kategori_id.php"
        val request = object: StringRequest(Request.Method.POST, url, Response.Listener { response->


            try {
                moviesList = ArrayList()
                val jsonObject = JSONObject(response)
                val movies = jsonObject.getJSONArray("filmler")

                for (i in 0 until movies.length()) {
                    val m = movies.getJSONObject(i)

                    val c = m.getJSONObject("kategori")
                    val category = Categories(c.getInt("kategori_id")
                                             ,c.getString("kategori_ad"))

                    val d = m.getJSONObject("yonetmen")
                    val director = Directors(d.getInt("yonetmen_id")
                                            ,d.getString("yonetmen_ad"))

                    val movie = Movies(m.getInt("film_id")
                                      ,m.getString("film_ad")
                                      ,m.getInt("film_yil")
                                      ,m.getString("film_resim")
                                      ,category
                                      ,director)

                    moviesList.add(movie)
                }
                adapterMovies = MoviesRecyclerViewAdapter(this, moviesList)
                bindingMoviesScreen.recyclerViewMovies.adapter = adapterMovies

            } catch (e:Exception) {
                e.printStackTrace()
            }
        }, Response.ErrorListener {  }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String,String>()
                params["kategori_id"] = category_id.toString()
                return params
            }
        }
        Volley.newRequestQueue(this@MoviesScreenActivity).add(request)
    }
}