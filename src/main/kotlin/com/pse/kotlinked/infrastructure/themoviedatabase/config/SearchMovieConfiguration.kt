package com.pse.kotlinked.infrastructure.themoviedatabase.config

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import com.pse.kotlinked.infrastructure.themoviedatabase.TheMovieDatabaseClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration


@Configuration
class SearchMovieConfiguration {

    companion object {
        const val TMDB_CONFIG_CACHE = "TMDB_CONFIG_CACHE"
    }

    @Autowired
    lateinit var tmdbSettings: TmdbSettings

    @Bean
    fun cacheManager(): CacheManager {
        return CaffeineCacheManager(TMDB_CONFIG_CACHE).apply {
            setCaffeine(
                Caffeine.newBuilder()
                    .maximumSize(1_0)
                    .expireAfterWrite(Duration.parse("PT1H"))
            )
        }
    }


    /**
     * Configuration using Retrofit java Builder
     */
    //@Bean
    fun searchClient(): TheMovieDatabaseClient {
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
            .create(TheMovieDatabaseClient::class.java)
    }

    /**
     * Configuration using custom Retrofit Dsl
     */
    @Bean
    fun searchClientDsl(): TheMovieDatabaseClient {
        return retrofit {
            baseUrl(tmdbSettings.baseUri)
            addConverterFactory(
                JacksonConverterFactory.create(createJacksonKotlinMapper())
            )
            client(
                okHttpClient {
                    addInterceptor(okHttpLoggingInterceptor())
                }
            )
        }
    }

    private fun createJacksonKotlinMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            setSerializationInclusion(NON_NULL)
            configure(INDENT_OUTPUT, true)
        }
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

}