package com.example.rozrywkafirebase.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rozrywkafirebase.*
import com.example.rozrywkafirebase.data.MovieAdapter
import com.example.rozrywkafirebase.data.MoviesRepository
import com.example.rozrywkafirebase.data.model.MovieEntity
import com.example.rozrywkafirebase.data.model.MovieViewEntity
import kotlinx.android.synthetic.main.fragment_home.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    var adapter: MovieAdapter? = null
    var MoviesList = ArrayList<MovieViewEntity>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadHomeMovieGrid()
    }

    private fun loadHomeMovieGrid() {
        MoviesRepository.getTopRatedMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onPopularMoviesError
        )

        adapter = MovieAdapter(this.context!!, MoviesList) {movie -> showMovieDetails(movie) }

        home_movies.adapter = adapter
    }

    private fun onPopularMoviesFetched(movies: List<MovieEntity>) {
        adapter?.updateMovies(movies)
    }

    private fun onPopularMoviesError() {
        Toast.makeText(this.activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: MovieEntity) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }
}
