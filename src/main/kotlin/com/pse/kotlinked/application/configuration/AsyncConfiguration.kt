package com.pse.kotlinked.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfiguration {

    @Bean(name = ["asyncTaskExecutor"])
    fun asyncTaskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 20
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("CriticsThread-")
        executor.initialize()
        return executor
    }
}