package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.Movie
import com.pse.kotlinked.domain.model.MovieId
import java.util.concurrent.CompletableFuture


interface CriticsRepositorySpi {
    fun search(movieName: String): List<Movie>
    fun searchCriticsAsync(movieId: MovieId): CompletableFuture<List<Critic>>
}