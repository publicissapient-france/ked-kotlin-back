package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.MovieId
import com.pse.kotlinked.infrastructure.themoviedatabase.config.TmdbSettings
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbReviewsResult
import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbReviewsResult.TmdbReviews
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.CompletableFuture

internal class TheMovieDatabaseAdapterTest {

    private val call: Call<TmdbReviewsResult> = mock {
        on { execute() } doReturn (Response.success(MOVIE_1_TMDB_REVIEW))
    }

    private val theMovieDatabaseClient: TheMovieDatabaseClient = mock {
        on { reviews(apiKey = API_KEY, movieId = MOVIE_1.stringValue()) } doReturn (call)

    }

    private val tmdbConfigurationService: TmdbConfigurationService = mock {
        on { getImagePath() } doReturn "https://image"
    }

    private val theMovieDatabaseAdapter = TheMovieDatabaseAdapter(
        theMovieDatabaseClient = theMovieDatabaseClient,
        tmdbSettings = TmdbSettings(apiKey = API_KEY, baseUri = "https://mock"),
        tmdbConfigurationService = tmdbConfigurationService
    )

    companion object {
        private val MOVIE_1 = MovieId(1)
        private val MOVIE_1_TMDB_REVIEW = TmdbReviewsResult(
            id = 100,
            page = 1,
            totalPages = 1,
            totalResults = 1,
            results = listOf(
                TmdbReviews(
                    id = "R1",
                    author = "Terminator",
                    content = "I'll be back"
                )
            )
        )
        private const val API_KEY = "API_KEY"
    }

    @Test
    internal fun `should call search critic and map the result`() {
        // GIVEN

        // WHEN
        val result = theMovieDatabaseAdapter.searchCriticsAsync(MOVIE_1)

        // THEN
        assertThat(result).isInstanceOf(CompletableFuture::class.java)
        assertThat(result.get()).isEqualTo(
            listOf(
                Critic(id = "R1", author = "Terminator", content = "I'll be back")
            )
        )
    }
}