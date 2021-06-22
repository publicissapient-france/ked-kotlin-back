package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.MovieCritics
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MovieService(val criticsRepositorySpi: CriticsRepositorySpi) : MovieServiceApi {
    private val logger = LoggerFactory.getLogger(MovieService::class.java)

    override fun searchCriticsFor(movieName: String): List<MovieCritics> {
        logger.info("Search Critics for [$name]")
        val movies = criticsRepositorySpi.search(movieName = name)
        logger.debug("Movies: $movies")
        return runBlocking {
            movies.map {
                async {
                    val critics = criticsRepositorySpi.searchCriticsAwait(movieId = it.id)
                    logger.info("Critic: $critics")
                    MovieCritics(
                        title = it.title,
                        description = it.description,
                        releaseDate = it.releaseDate,
                        note = it.note * 10.0,
                        critics = critics,
                        imageURl = it.posterUrl
                    )
                }
            }.awaitAll()
        }
    }


}