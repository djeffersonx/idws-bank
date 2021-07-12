package br.com.idws.bank.accountvalidator.infrastructure.validators

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CoreBankingValidator(
    @Value("\${core-banking.url}")
    val coreBankingUrl: String,
    objectMapper: ObjectMapper
) : AbstractValidator("$coreBankingUrl/consumer/validation/create", objectMapper)