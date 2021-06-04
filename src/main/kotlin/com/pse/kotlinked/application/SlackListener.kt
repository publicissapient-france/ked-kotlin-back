package com.pse.kotlinked.application

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.App
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.handler.BoltEventHandler
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.model.event.AppMentionEvent
import com.slack.api.model.event.MessageEvent
import com.slack.api.socket_mode.request.EventsApiEnvelope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SlackListener {
    private val logger = LoggerFactory.getLogger(SlackListener::class.java)

    private val app = App()
        .command("/movie", MovieHandler())
        .message("^movie (.*)", MessageHandler())
        .event(AppMentionEvent::class.java) { event, ctx ->
            ctx.say("<@${event.event.user}> What's up?")
            ctx.ack()
        }

    init {
        SocketModeApp("xapp-1-A022URLMLL8-2085697387297-f5730b3fab1164b9ba25c8f07e4877315304c879a83adba1a776f8fe359466e9", app).start()
    }

    private fun handleEvents(events: EventsApiEnvelope) {
        logger.info("Received event: {}", events)
    }

    class MovieHandler : SlashCommandHandler {
        private val logger = LoggerFactory.getLogger(MovieHandler::class.java)
        override fun apply(req: SlashCommandRequest, context: SlashCommandContext): Response {
            logger.info("request: {}\n context: {}", req, context)
            return context.ack("TODO: find critics for: ${req.payload.text}")
        }
    }

    private class MessageHandler: BoltEventHandler<MessageEvent> {
        private val logger = LoggerFactory.getLogger(MessageHandler::class.java)
        override fun apply(event: EventsApiPayload<MessageEvent>, context: EventContext): Response {
            logger.info("event: {}\n context: {}", event, context)
            return context.ack(context.toJson("Message received"))
        }
    }
}