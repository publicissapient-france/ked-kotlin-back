package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.domain.CriticsRepositorySpi
import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.Movie
import com.pse.kotlinked.domain.model.MovieId
import com.pse.kotlinked.infrastructure.Adapter
import com.pse.kotlinked.infrastructure.themoviedatabase.config.TmdbSettings
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbReviewsResult
import org.glassfish.grizzly.utils.Charsets.UTF8_CHARSET
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import retrofit2.Response
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture

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
                        id = MovieId( it.id),
                        title = it.title,
                        description = it.overview ?: "No description",
                        note = it.voteAverage ?: 0.0,
                        releaseDate = it.releaseDateAsLocalDate,
                        posterUrl = it.posterPath?.let { posterPath -> tmdbConfigurationService.getImagePath() + posterPath }
                    )
                }
                ?: listOf()
        }
        return listOf()
    }


    @Async("asyncTaskExecutor")
    override fun searchCriticsAsync(movieId: MovieId): CompletableFuture<List<Critic>> {
        logger.debug("calling async critics for movie: {}", movieId)
        val result = theMovieDatabaseClient.reviews(apiKey = tmdbSettings.apiKey, movieId = movieId.stringValue()).execute()

        return CompletableFuture.completedFuture(mapCriticsResult(result))
    }

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