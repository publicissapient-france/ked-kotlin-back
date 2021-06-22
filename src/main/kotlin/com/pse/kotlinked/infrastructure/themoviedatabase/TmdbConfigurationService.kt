package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.infrastructure.themoviedatabase.config.SearchMovieConfiguration.Companion.TMDB_CONFIG_CACHE
import com.pse.kotlinked.infrastructure.themoviedatabase.config.TmdbSettings
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class TmdbConfigurationService(val theMovieDatabaseClient: TheMovieDatabaseClient, val tmdbSettings: TmdbSettings) {
    private val logger = LoggerFactory.getLogger(TmdbConfigurationService::class.java)

    @Cacheable(TMDB_CONFIG_CACHE)
    fun getImagePath(): String {
        val result = theMovieDatabaseClient.configuration(tmdbSettings.apiKey).execute()
        logger.debug("Configuration: {}", result.body())
        if (result.isSuccessful) {
            val configuration = result.body()!!
            return configuration.images.baseUrl + configuration.images.posterSizes[0]
        }
        throw IllegalStateException("Cannot fetch TMDB configuration: " +result.errorBody()?.string())
    }
}