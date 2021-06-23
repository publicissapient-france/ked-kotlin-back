package com.pse.kotlinked.application

import com.pse.kotlinked.domain.MovieServiceApi
import com.pse.kotlinked.domain.model.MovieCritics
import com.slack.api.bolt.App
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.kotlin_extension.block.withBlocks
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SlackListener(val movieServiceApi: MovieServiceApi) {

    private val app = App().apply {
        command("/movie", MovieHandler(movieServiceApi))
    }

    init {
        SocketModeApp("xapp-1-A022URLMLL8-2201149940755-cb000db7879e2dfe6aab785f138bd76c52e08e07119eff35d9a8edf51c4dfeb0", app).startAsync()
    }


    class MovieHandler(private val movieServiceApi: MovieServiceApi) : SlashCommandHandler {
        private val logger = LoggerFactory.getLogger(MovieHandler::class.java)
        override fun apply(req: SlashCommandRequest, context: SlashCommandContext): Response {
            logger.info("Command received: {}\n context: {}", req, context)
            val critics = movieServiceApi.searchCriticsFor(req.payload.text)
            logger.debug("MovieCritics: {}", critics)
            return context.ack(createResponse(movieName = req.payload.text, critics = critics))
        }

        private fun createResponse(movieName: String, critics: List<MovieCritics>): List<LayoutBlock> {
            return withBlocks {
                header {
                    text(text = "Resultat de la recherche pour: $movieName")
                }
                critics.map {
                    divider()
                    header {
                        text(text = "Titre: ${it.title}")
                    }
                    section {
                        markdownText(
                            """
                            *Description:*
                            ${it.description}
                            *Note:* ${it.note} %
                        """.trimIndent()
                        )
                        if (it.imageURl != null) {
                            accessory {
                                image(
                                    imageUrl = it.imageURl,
                                    altText = "Poster"
                                )
                            }
                        }
                    }
                    if (it.critics.isNotEmpty()) {
                        section {
                            it.critics.map { critic ->
                                markdownText(
                                    """
                                *Critic rédigé par* ${critic.author}
                                ${critic.content}
                                *note:* ${critic.star}
                            """.trimIndent()
                                )
                            }
                        }
                    }
                }
            }.run {
                this.subList(0, minOf(50, this.size)) // ugly hack : slack authorize only 50 elements
            }
        }
    }


}