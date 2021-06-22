package com.pse.kotlinked.domain.model

import java.time.LocalDate


data class MovieCritics (
    val title: String,
    val description: String,
    val releaseDate: LocalDate?,
    val note: Double,
    val critics: List<Critic>,
    val imageURl: String?
)