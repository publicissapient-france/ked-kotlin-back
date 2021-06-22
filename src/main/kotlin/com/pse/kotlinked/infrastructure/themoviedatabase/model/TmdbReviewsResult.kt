package com.pse.kotlinked.infrastructure.themoviedatabase.model

import com.fasterxml.jackson.annotation.JsonProperty


data class TmdbReviewsResult(
    val id: Int,
    val page: Int,
    @JsonProperty("total_pages")
    val totalPages:Int,
    @JsonProperty("total_results")
    val totalResults:Int,
    val results: List<TmdbReviews>
) {
    data class TmdbReviews(
        val id: String,
        val author: String,
        val content: String,
        @JsonProperty("author_details")
        val authorDetails: TmdbReviewsAuthorDetail? = null,
        val url: String? = null
    )

    data class TmdbReviewsAuthorDetail(
        val name: String,
        val username: String? = null,
        val rating: Int? = null
    )
}