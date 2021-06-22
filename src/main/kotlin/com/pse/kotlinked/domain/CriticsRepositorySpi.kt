package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.Movie


interface CriticsRepositorySpi {
    fun search(movieName: String): List<Movie>
    fun searchCritics(movieId: Int): List<Critic>

    suspend fun searchCriticsAwait(movieId: Int): List<Critic>
}