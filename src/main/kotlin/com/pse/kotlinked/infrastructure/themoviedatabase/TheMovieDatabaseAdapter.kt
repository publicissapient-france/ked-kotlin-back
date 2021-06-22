package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.domain.CriticsRepositorySpi
import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.Movie
import com.pse.kotlinked.infrastructure.Adapter
import com.pse.kotlinked.infrastructure.themoviedatabase.config.TmdbSettings
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbReviewsResult
import org.glassfish.grizzly.utils.Charsets.UTF8_CHARSET
import org.slf4j.LoggerFactory
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.URLEncoder
import java.time.LocalDate

@Adapter
class TheMovieDatabaseAdapter(
    val theMovieDatabaseClient: TheMovieDatabaseClient,
    val tmdbSettings: TmdbSettings,
    val tmdbConfigurationService: TmdbConfigurationService
) : CriticsRepositorySpi {
    private val logger = LoggerFactory.getLogger(TheMovieDatabaseAdapter::class.java)

    override fun search(movieName: String): List<Movie> {
        val result = theMovieDatabaseClient.searchMovies(
            apiKey = tmdbSettings.apiKey, query = URLEncoder.encode(movieName, UTF8_CHARSET)
        ).execute()

        if (result.isSuccessful) {
            logger.debug("TMDB result: {}", result.body())
            return result.body()?.results
                ?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        description = it.overview ?: "No description",
                        note = it.voteAverage ?: 0.0,
                        releaseDate = it.releaseDate?.let { releaseDate ->
                            try {
                                LocalDate.parse(releaseDate)
                            } catch (e: Exception) {
                                logger.error("Error while reading releaseDate: {}", releaseDate)
                                null
                            }
                        },
                        posterUrl = it.posterPath?.let { posterPath -> tmdbConfigurationService.getImagePath() + posterPath }
                    )
                }
                ?: listOf()
        }
        return listOf()
    }

    /**
     * Imperative code
     */
    override fun searchCritics(movieId: Int): List<Critic> {
        val result = theMovieDatabaseClient.reviews(apiKey = tmdbSettings.apiKey, movieId = movieId.toString()).execute()

        return mapCriticsResult(result)
    }

    /**
     * Asynchronous code
     */
    override suspend fun searchCriticsAwait(movieId: Int): List<Critic> {
        logger.debug("calling critics for movie: {}", movieId)
        val result = theMovieDatabaseClient.reviews(apiKey = tmdbSettings.apiKey, movieId = movieId.toString()).awaitResponse()

        return mapCriticsResult(result)
    }

    /**
     * This fonction can be called from coroutine or normal code without changing is signature !!!
     */
    private fun mapCriticsResult(result: Response<TmdbReviewsResult>): List<Critic> {
        if (result.isSuccessful) {
            logger.debug("TMDB critics result: {}", result.body())
            return result.body()?.results
                ?.map {
                    Critic(
                        id = it.id,
                        author = it.author,
                        content = it.content,
                        rating = it.authorDetails?.rating
                    )
                }
                ?: listOf()
        }
        return listOf()
    }
}