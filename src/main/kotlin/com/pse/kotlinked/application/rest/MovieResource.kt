package com.pse.kotlinked.application.rest

import com.pse.kotlinked.domain.MovieServiceApi
import com.pse.kotlinked.domain.model.MovieCritics
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieResource(val movieServiceApi: MovieServiceApi) : MovieResourceApi {
    private val logger = LoggerFactory.getLogger(MovieResource::class.java)

    override fun getMovie(name: String): List<MovieCritics> {
        logger.info("Requesting critics for movie: $name")
        return movieServiceApi.searchCriticsFor(name)
    }
}