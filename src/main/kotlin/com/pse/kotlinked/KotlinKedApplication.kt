package com.pse.kotlinked

import com.pse.kotlinked.application.model.FilmResponseDto
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinKedApplication

fun main(args: Array<String>) {

    //non-null types
    //ne peut pas etre null si on ne lle precise comme nullable
    val logger = LoggerFactory.getLogger(KotlinKedApplication::class.java)
    //peut etre null car ajout du ?
    var demoVarNullable: String? = "test de nullable"
    demoVarNullable = null
    logger.info("longueur de la String ${demoVarNullable?.length}")
    //peut pas compiler car nullable ajout du '?' pour passer la compilation
    //val length = demoVarNullable.length
    logger.info("longuer de la String avec ? et valeur null ==> ${demoVarNullable?.length}")
    var demoNotNullValue: String = "not null value"
    //ne peut pas compiler car non nullable
    //demoNotNullValue=null
    //value not null donc utilisable sans erreur
    val lengthNotNull = demoNotNullValue.length
    logger.info("longuer de la chaine non nullable ==> ${demoNotNullValue.length}")


    //immuabilité "simple"
    val immutableString = "immutable"
    //immutableString="modify immutable string"

    val listWithNulls: List<FilmResponseDto?> = listOf(FilmResponseDto(id = "1", title = "first"), null)
    for (item in listWithNulls) {
        item?.let { logger.info(it.toString()) } // prints Kotlin and ignores null
    }

    val original = "abc"
// Evolve the value and send to the next chain
    original.let {
        println("The original String is $it") // "abc"
        it.reversed() // evolve it as parameter to send to next let
    }.let {
        println("The reverse String is $it") // "cba"
        it.length  // can be evolve to other type
    }.let {
        println("The length of the String is $it") // 3
    }
    println("original: $original")
// Wrong
// Same value is sent in the chain (printed answer is wrong)
    original.also {
        println("The original String is $it") // "abc"
        it.reversed() // even if we evolve it, it is useless
    }.also {
        println("The reverse String is ${it}") // "abc"
        it.length  // even if we evolve it, it is useless
    }.also {
        println("The length of the String is ${it}") // "abc"
    }
    println("original: $original")
// Corrected for also (i.e. manipulate as original string
// Same value is sent in the chain
    original.also {
        println("The original String is $it") // "abc"
    }.also {
        println("The reverse String is ${it.reversed()}") // "cba"
    }.also {
        println("The length of the String is ${it.length}") // 3
    }
    println("original: $original")
    runApplication<KotlinKedApplication>(*args)
}
