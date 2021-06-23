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
        logger.info("Search Critics for [$movieName]")
        val movies = criticsRepositorySpi.search(movieName = movieName)
        logger.debug("Movies: $movies")
        return runBlocking {                // This is the bridge between imperative code and coroutine. It will block the current thread until the coroutine is finished
            movies.map {
                async {                     // With this we will map all the collection asynchronously. Meaning we will call the critics in parallel
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
            }.awaitAll()            // work with the async {}  to wait that all asynchronous call during the mapping of the collection is done. Await is not blocking
        }
    }


}