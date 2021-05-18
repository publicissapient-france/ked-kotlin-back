package com.pse.kotlinked.infrastructure.model

import java.util.*

data class MovieResultsPage(
    var id: Int? = null,
    var page: Int? = null,
    var total_pages: Int? = null,
    var total_results: Int? = null,
    var results: List<Movie>? = null

) {
    data class Movie(
        val id: Int,

        val adult: Boolean?,
        val backdrop_path: String?,
        val genres: List<Genre>?,
        val genre_ids: List<Int>?,
        val original_title: String?,
        val original_language: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val release_date: Date?,
        val title: String?,
        val vote_average: Double?,
        val vote_count: Int?,
        val media_type: String?
    )
}
