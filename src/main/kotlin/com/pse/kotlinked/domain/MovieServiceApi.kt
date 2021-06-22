package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.MovieCritics


interface MovieServiceApi {
    fun searchCriticsFor(movieName: String): List<MovieCritics>
}