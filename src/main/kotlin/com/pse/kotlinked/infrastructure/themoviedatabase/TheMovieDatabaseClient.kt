package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbConfiguration
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbReviewsResult
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbSearchMoviesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface TheMovieDatabaseClient {

    enum class LANGUAGE(val value: String) {
        FRENCH("fr"),
        ENGLISH("en");
    }

    enum class REGION(val value: String) {
        FRANCE("FR")
    }

    @Headers("Content-Type: application/json")
    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = LANGUAGE.FRENCH.value,
        @Query("region") region: String = REGION.FRANCE.value,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("year") year: Int? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null
    ): Call<TmdbSearchMoviesResult>

    @Headers("Content-Type: application/json")
    @GET("movie/{movieId}/reviews")
    fun reviews(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = LANGUAGE.FRENCH.value,
        @Query("page") page: Int = 1,
    ) : Call<TmdbReviewsResult>


    @Headers("Content-Type: application/json")
    @GET("configuration")
    fun configuration(
        @Query("api_key") apiKey: String
    ) : Call<TmdbConfiguration>

}