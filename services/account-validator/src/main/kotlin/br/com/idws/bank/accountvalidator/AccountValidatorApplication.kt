package br.com.idws.bank.accountvalidator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AccountValidatorApplication {

    @Bean
    fun objectMapper() = br.com.idws.bank.accountvalidator.objectMapper()

}

fun main(args: Array<String>) {
    runApplication<AccountValidatorApplication>(*args)
}

fun objectMapper() = ObjectMapper().apply {
    registerModule(JavaTimeModule())
    registerModule(KotlinModule())
}