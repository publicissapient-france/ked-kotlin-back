package com.pse.kotlinked.application.rest

import com.pse.kotlinked.application.model.FilmResponseDto
import com.pse.kotlinked.domain.SearchServiceApi
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchResource(val searchService: SearchServiceApi) : SearchResourceApi {
    private val logger = LoggerFactory.getLogger(SearchResource::class.java)

    override fun movie(name: String): List<FilmResponseDto> {
        logger.info("search movie: $name")
        return searchService.movie(name).map {
            FilmResponseDto.fromDomain(
                it
            )
        }

    }
}