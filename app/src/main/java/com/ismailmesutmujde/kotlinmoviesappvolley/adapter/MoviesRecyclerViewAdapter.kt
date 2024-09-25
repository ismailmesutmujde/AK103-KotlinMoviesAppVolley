package com.ismailmesutmujde.kotlinmoviesappvolley.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ismailmesutmujde.kotlinmoviesappvolley.R
import com.ismailmesutmujde.kotlinmoviesappvolley.model.Movies
import com.ismailmesutmujde.kotlinmoviesappvolley.view.MoviesDetailScreenActivity
import com.squareup.picasso.Picasso

class MoviesRecyclerViewAdapter (private val mContext: Context, private val moviesList:List<Movies>)
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(view: View) : RecyclerView.ViewHolder(view) {
        var movie_card: CardView
        var textViewMovieName: TextView
        var imageViewMoviePoster: ImageView

        init {
            movie_card = view.findViewById(R.id.movie_card)
            textViewMovieName = view.findViewById(R.id.textViewMovieName)
            imageViewMoviePoster = view.findViewById(R.id.imageViewMoviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val design = LayoutInflater.from(mContext).inflate(R.layout.movie_card_design, parent, false)
        return CardDesignHolder(design)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val movie = moviesList.get(position)
        holder.textViewMovieName.text = movie.movie_name

        val url = "http://kasimadalan.pe.hu/filmler/resimler/${movie.movie_poster}"
        Picasso.get().load(url).into(holder.imageViewMoviePoster)
        //holder.imageViewMoviePoster.setImageResource(mContext.resources.getIdentifier(movie.movie_poster,"drawable",mContext.packageName))

        holder.movie_card.setOnClickListener {
            val intent = Intent(mContext, MoviesDetailScreenActivity::class.java)
            intent.putExtra("movieObject", movie)
            mContext.startActivity(intent)
        }

    }
}