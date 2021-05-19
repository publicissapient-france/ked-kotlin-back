package com.pse.kotlinked.infrastructure.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.pse.kotlinked.infrastructure.SearchClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


@Configuration
class SearchMovieConfiguration {

    @Autowired
    lateinit var tmdbSettings: TmdbSettings


    @Bean
    fun searchClient(): SearchClient {
        val okHttpClient: okhttp3.OkHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(okHttpLoggingInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(tmdbSettings.baseUri)
            .addConverterFactory(
                JacksonConverterFactory.create(createJacksonKotlinMapper())
            )
            .client(okHttpClient)
            .build()
            .create(SearchClient::class.java)
    }


    @Bean
    fun okHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = LoggerFactory.getLogger(SearchMovieConfiguration::class.java)
        val debugLogger: HttpLoggingInterceptor.Logger = HttpLoggingInterceptor.Logger {
            logger.trace(it)
        }
        return HttpLoggingInterceptor(debugLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    private fun createJacksonKotlinMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            registerKotlinModule()
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }
}