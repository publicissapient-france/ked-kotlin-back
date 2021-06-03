package com.pse.kotlinked.infrastructure

import com.pse.kotlinked.domain.SearchClientSpi
import com.pse.kotlinked.domain.model.Film
import org.springframework.stereotype.Component

@Component
class SearchClientAdapter(val searchClient: SearchClient) : SearchClientSpi {
    override fun movie(name: String): List<Film> {
        val response = searchClient.movie(query = name, page = null, year = null, primaryReleaseYear = null, region = null).execute()
        if (response.isSuccessful) {
            return response.body()
                ?.let {
                    return it.toDomain()
                }
                ?: throw IllegalStateException("Bnpp response was successful but body was empty ??? : $response")
        }
        return listOf()
    }
}