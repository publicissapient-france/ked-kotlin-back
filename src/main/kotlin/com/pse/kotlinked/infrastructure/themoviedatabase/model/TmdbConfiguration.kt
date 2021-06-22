package com.pse.kotlinked.infrastructure.themoviedatabase.model

import com.fasterxml.jackson.annotation.JsonProperty


data class TmdbConfiguration(
    val images: TmdbImagesConfiguration,
    @JsonProperty("change_keys")
    val changeKeys: List<String>
) {
    data class TmdbImagesConfiguration(
        @JsonProperty("base_url")
        val baseUrl: String,
        @JsonProperty("secure_base_url")
        val secureBaseURl: String,
        @JsonProperty("backdrop_sizes")
        val backdropSizes: List<String>,
        @JsonProperty("logo_sizes")
        val logoSizes: List<String>,
        @JsonProperty("poster_sizes")
        val posterSizes: List<String>,
        @JsonProperty("profile_sizes")
        val profileSizes: List<String>,
        @JsonProperty("still_sizes")
        val stillSizes: List<String>,
    )
}