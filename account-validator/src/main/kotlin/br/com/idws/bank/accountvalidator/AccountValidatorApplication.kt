package br.com.idws.bank.accountvalidator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener


@SpringBootApplication
class AccountValidatorApplication {

    companion object {
        var applicationContext: ApplicationContext? = null
        fun getObjectMapper() = applicationContext!!.getBean(ObjectMapper::class.java)
    }

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        registerModule(KotlinModule())
    }

    @EventListener(ContextRefreshedEvent::class)
    fun contextRefreshedEvent(contextRefreshEvent: ContextRefreshedEvent) {
        applicationContext = contextRefreshEvent.applicationContext
    }

}

fun main(args: Array<String>) {
    runApplication<AccountValidatorApplication>(*args)
}


