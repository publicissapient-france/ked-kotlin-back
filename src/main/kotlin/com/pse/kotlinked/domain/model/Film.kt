package com.pse.kotlinked.domain.model

data class Film(
    val title: String = "E.T",
    val id: String,
    val description: String
) {

    private constructor(builder: Builder) : this(builder.id, builder.title, builder.description)

    companion object {
        inline fun build(id: String, title: String, description: String, block: Builder.() -> Unit) = Builder(id, title, description).apply(block).build()
    }

    class Builder(var title: String,
                  var description: String,
                  var id: String
    ) {

        fun build() = Film(this)
    }
}
