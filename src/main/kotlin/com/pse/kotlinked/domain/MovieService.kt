package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.MovieCritics
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class MovieService(
    val criticsRepositorySpi: CriticsRepositorySpi,
) : MovieServiceApi {
    private val logger = LoggerFactory.getLogger(MovieService::class.java)

    override fun searchCriticsFor(movieName: String): List<MovieCritics> {
        logger.info("Search Critics using spring @Async for [$movieName]")
        val movies = criticsRepositorySpi.search(movieName = movieName)
        logger.debug("Movies: $movies")

        return movies.map { movie ->
            criticsRepositorySpi.searchCriticsAsync(movieId = movie.id)
                .thenApply {
                    MovieCritics(
                        title = movie.title,
                        description = movie.description,
                        releaseDate = movie.releaseDate,
                        note = movie.note * 10.0,
                        critics = it,
                        imageURl = movie.posterUrl
                    )
                }
        }.also {
            CompletableFuture.allOf(*it.toTypedArray()).join()
        }.map {
            it.get()
        }
    }
}