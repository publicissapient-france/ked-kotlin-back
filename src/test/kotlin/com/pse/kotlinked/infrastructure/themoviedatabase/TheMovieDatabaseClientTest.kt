package com.pse.kotlinked.infrastructure.themoviedatabase

import com.pse.kotlinked.infrastructure.themoviedatabase.model.TmdbSearchMoviesResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import retrofit2.Call

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles(value = ["test"])
class TheMovieDatabaseClientTest {

    @Autowired
    lateinit var theMovieDatabaseClient: TheMovieDatabaseClient

    @Test
    fun test_movieSearch() {
        val call: Call<TmdbSearchMoviesResult> = theMovieDatabaseClient.searchMovies(
            apiKey="ec111e07c4ae78be4beb39617994c039",
            query = "tenet"
        )
        val tmdbSearchMoviesResults: TmdbSearchMoviesResult? = call.execute().body()
        assertThat(tmdbSearchMoviesResults).isNotNull
    }
}