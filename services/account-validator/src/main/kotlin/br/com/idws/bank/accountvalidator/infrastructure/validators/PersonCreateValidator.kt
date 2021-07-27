package br.com.idws.bank.accountvalidator.infrastructure.validators

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PersonCreateValidator(
    @Value("\${person.url}")
    val personUrl: String,
    objectMapper: ObjectMapper
) : AbstractValidator("$personUrl/validation/create", objectMapper)