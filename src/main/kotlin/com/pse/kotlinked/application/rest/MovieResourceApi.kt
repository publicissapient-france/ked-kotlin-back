package com.pse.kotlinked.application.rest

import com.pse.kotlinked.domain.model.MovieCritics
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/film")
interface MovieResourceApi {
    @GetMapping("/{name}")
    fun getMovie(@PathVariable("name") name: String): List<MovieCritics>
}