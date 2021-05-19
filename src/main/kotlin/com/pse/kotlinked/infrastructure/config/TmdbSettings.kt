package com.pse.kotlinked.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "tmdb")
data class TmdbSettings(

    val api_key: String,
    val baseUri: String
)