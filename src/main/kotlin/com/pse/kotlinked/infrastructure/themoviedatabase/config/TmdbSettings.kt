package com.pse.kotlinked.infrastructure.themoviedatabase.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "tmdb")
data class TmdbSettings(

    val apiKey: String,
    val baseUri: String
)