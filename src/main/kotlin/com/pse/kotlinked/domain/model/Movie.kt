package com.pse.kotlinked.domain.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: LocalDate?,
    val note: Double,
    val posterUrl: String?
)