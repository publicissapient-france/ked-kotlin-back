package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Film

interface SearchClientSpi {
    fun movie(name: String): List<Film>
}