package com.example.rozrywkafirebase.data.api

import com.example.rozrywkafirebase.data.model.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object{
        val API_KEY = "a72f665fa59792ecef66b222fc8f3629"
    }
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): retrofit2.Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): retrofit2.Call<GetMoviesResponse>

    @GET("search/movie")
    fun getSearchMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("query") query: String
    ): retrofit2.Call<GetMoviesResponse>
}