package com.pse.kotlinked.domain.model

data class Critic(
    val id: String,
    val author: String,
    val content: String,
    val rating: Int? = null,
) {
    val star: String
        get() {
            if (rating != null) {
                return "".padEnd(rating, '*')
            }
            return ""
        }
}
