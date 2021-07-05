package com.pse.kotlinked.domain


import com.pse.kotlinked.domain.model.Critic
import com.pse.kotlinked.domain.model.Movie
import com.pse.kotlinked.domain.model.MovieCritics
import com.pse.kotlinked.domain.model.MovieId
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.util.concurrent.CompletableFuture


internal class MovieServiceTest {

    private val criticsRepositorySpi: CriticsRepositorySpi = mock {
        on { search("Terminator") } doReturn listOf(
            Movie(id = MovieId(1), title = "Terminator", description = "I will be back", posterUrl = "terminator.jpg", releaseDate = LocalDate.parse("1985-04-24"), note = 10.0)
        )
        on { searchCriticsAsync(MovieId(1)) } doReturn CompletableFuture.completedFuture(
            listOf(
                Critic(id = "c1", author = "Proust", content = "Le meilleur film que j'ai jamais vu", rating = 5)
            )
        )
    }

    private val movieService = MovieService(criticsRepositorySpi = criticsRepositorySpi)


    @Test
    internal fun `should work`() {
        // Given

        // When
        val result = movieService.searchCriticsFor("Terminator")

        // Then
        Assertions.assertThat(result).isEqualTo(
            listOf(
                MovieCritics(
                    title = "Terminator",
                    description = "I will be back",
                    imageURl = "terminator.jpg",
                    releaseDate = LocalDate.parse("1985-04-24"),
                    note = 100.0,
                    critics = listOf(
                        Critic(id = "c1", author = "Proust", content = "Le meilleur film que j'ai jamais vu", rating = 5)
                    )
                )
            )
        )

        verify(criticsRepositorySpi).search("Terminator")
        verify(criticsRepositorySpi).searchCriticsAsync(MovieId(1))
    }
}