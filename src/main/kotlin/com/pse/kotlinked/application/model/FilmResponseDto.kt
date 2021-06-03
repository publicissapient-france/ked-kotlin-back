package com.pse.kotlinked.application.model

import com.pse.kotlinked.domain.model.Film

data class FilmResponseDto(
    val id: String,
    val title: String,
    val description: String
) {
    companion object {
        fun fromDomain(film: Film): FilmResponseDto {
            return FilmResponseDto(
                id = film.id,
                title = film.title,
                description = film.description
            )
        }
    }
}
