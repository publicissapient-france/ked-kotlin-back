package com.pse.kotlinked.infrastructure

import com.pse.kotlinked.infrastructure.model.MovieResultsPage
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import retrofit2.Call
import java.io.IOException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles(value = ["test"])
class SearchClientTest {
    private val logger = LoggerFactory.getLogger(SearchClientTest::class.java)
    @Autowired
    lateinit var searchClient: SearchClient

    @Test
    @Throws(IOException::class)
    fun test_movieSearch() {
        val call: Call<MovieResultsPage> = searchClient.movie(
            api_key="ec111e07c4ae78be4beb39617994c039",
            query = "tenet",
            null,
            null,
            null,
            null,
            null,
            null
        )
        val movieResults: MovieResultsPage? = call.execute().body()
        logger.info(movieResults.toString())
    }
}