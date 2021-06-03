package com.pse.kotlinked.domain

import com.pse.kotlinked.domain.model.Film
import org.springframework.stereotype.Service

@Service
class SearchService(val searchClientSpi: SearchClientSpi) : SearchServiceApi {
    override fun movie(filter: String): List<Film> {
        return searchClientSpi.movie(name = filter)
    }
}