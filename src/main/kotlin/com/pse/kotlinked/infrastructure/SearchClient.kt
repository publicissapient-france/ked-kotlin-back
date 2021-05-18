package com.pse.kotlinked.infrastructure

import com.pse.kotlinked.infrastructure.model.MovieResultsPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchClient {

    @Headers("Content-Type: application/json")
    @GET("search/movie")
    fun movie(
        @Query("api_key") api_key: String? = "ec111e07c4ae78be4beb39617994c039",
        @Query("query") query: String?,
        @Query("page") page: Int?,
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("year") year: Int?,
        @Query("primary_release_year") primaryReleaseYear: Int?
    ): Call<MovieResultsPage>


}