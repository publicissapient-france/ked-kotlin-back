package com.pse.kotlinked.application.rest

import com.pse.kotlinked.application.model.FilmResponseDto
import com.pse.kotlinked.domain.FilmServiceApi
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoResource(filmResourceService: FilmServiceApi) : DemoResourceApi {
    private val logger = LoggerFactory.getLogger(DemoResource::class.java)
    override fun getFilm(id: String): FilmResponseDto {

        return FilmResponseDto(id = "1", "test")

    }
}