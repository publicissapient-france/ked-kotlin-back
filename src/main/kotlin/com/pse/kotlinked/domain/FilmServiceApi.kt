package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Film

interface FilmServiceApi {
    fun getFilteredFilmFromTop(filter: String): List<Film>
}