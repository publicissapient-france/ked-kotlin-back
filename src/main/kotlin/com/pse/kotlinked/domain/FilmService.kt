package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Film
import org.springframework.stereotype.Service

@Service
class FilmService : FilmServiceApi {
    override fun getFilteredFilmFromTop(filter: String): List<Film> {
        return listOf(
            Film(id = "1", title = "first film of list", description = "the best of the best"),
            Film(id = "2", title = "second title of list", description = "it will not be in list")
        )
    }
}