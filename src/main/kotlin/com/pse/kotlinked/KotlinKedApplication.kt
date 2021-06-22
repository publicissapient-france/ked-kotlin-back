package com.pse.kotlinked

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
@ConfigurationPropertiesScan
class KotlinKedApplication

fun main(args: Array<String>) {

    runApplication<KotlinKedApplication>(*args)
}
