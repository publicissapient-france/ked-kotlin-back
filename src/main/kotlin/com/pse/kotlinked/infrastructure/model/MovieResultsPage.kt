package com.pse.kotlinked.infrastructure.model

import com.pse.kotlinked.domain.model.Film
import java.util.*

data class MovieResultsPage(
    var id: Int? = null,
    var page: Int? = null,
    var totalPages: Int? = null,
    var totalResults: Int? = null,
    var results: List<Movie>? = null

) {
    fun toDomain(): List<Film> {
        return results?.map {
            Film("", "", "")
        } ?: listOf()
    }

    data class Movie(
        val id: Int,

        val adult: Boolean?,
        val backdropPath: String?,
        val genres: List<Genre>?,
        val genreIds: List<Int>?,
        val originalTitle: String?,
        val originalLanguage: String?,
        val overview: String?,
        val popularity: Double?,
        val posterPath: String?,
        val releaseDate: Date?,
        val title: String?,
        val voteAverage: Double?,
        val voteCount: Int?,
        val mediaType: String?
    )
}
