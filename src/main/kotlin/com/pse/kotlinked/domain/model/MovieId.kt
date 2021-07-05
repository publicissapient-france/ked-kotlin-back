package com.pse.kotlinked.domain.model

@JvmInline
value class MovieId(val value: Int) {

    fun stringValue() = value.toString()
}