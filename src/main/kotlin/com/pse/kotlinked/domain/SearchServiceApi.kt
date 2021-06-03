package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Film

interface SearchServiceApi {
    fun movie(name: String): List<Film>
}