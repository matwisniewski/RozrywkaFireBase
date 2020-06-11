package com.example.rozrywkafirebase.data

import android.content.Context
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.rozrywkafirebase.R
import com.example.rozrywkafirebase.data.model.MovieEntity
import com.example.rozrywkafirebase.data.model.MovieViewEntity
import kotlinx.android.synthetic.main.movie_entry.view.*

class MovieAdapter(context: Context, foodsList: ArrayList<MovieViewEntity>, val onMovieClick: (movie:MovieEntity) -> Unit) : BaseAdapter() {
    var foodsList = foodsList
    var context: Context? = context
    var moviesFullInfo : MutableList<MovieEntity> = mutableListOf<MovieEntity>();

    override fun getCount(): Int {
        return foodsList.size
    }

    override fun getItem(position: Int): Any {
        return foodsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val food = this.foodsList[position]

        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val foodView = inflator.inflate(R.layout.movie_entry, null)

        Glide.with(foodView)
            .load("https://image.tmdb.org/t/p/w342${foodsList[position].image}")
            .transform(CenterCrop())
            .into(foodView.imgMovie)

        foodView.movieTitle.text = food.name!!
        foodView.setOnClickListener{
            onMovieClick.invoke(moviesFullInfo[position])
        }

        return foodView
    }

    fun updateMovies(movies: List<MovieEntity>) {
        moviesFullInfo = movies.toMutableList()
        val list : MutableList<MovieViewEntity> = mutableListOf()
        movies.forEach{
            list.add(MovieViewEntity(it.title, it.posterPath))
        }
        this.foodsList = ArrayList(list)
        notifyDataSetChanged()
    }
}
