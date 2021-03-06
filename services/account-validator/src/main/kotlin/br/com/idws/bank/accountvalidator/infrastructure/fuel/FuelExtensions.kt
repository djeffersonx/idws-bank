package br.com.idws.bank.accountvalidator.infrastructure.fuel

import br.com.idws.bank.accountvalidator.domain.model.validation.Error
import br.com.idws.bank.accountvalidator.objectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import org.springframework.http.HttpStatus

fun Request.getOrError(
    onSuccess: (Request, Response, Result<String, FuelError>) -> Unit
): Any {

    val (request, response, result) = responseString()

    return when (result) {
        is Result.Success -> {
            onSuccess(request, response, result)
        }
        is Result.Failure -> {
            return if (createHttpStatus(request, response, result).is4xxClientError) {
                objectMapper().readValue<List<Error>>(String(response.data))
            } else {
                throw CommunicationException.of(request, response, result)
            }
        }
    }

}

private fun createHttpStatus(request: Request, response: Response, result: Result<String, FuelError>): HttpStatus {
    if (response.statusCode < 100) {
        CommunicationException.of(request, response, result)
    }
    return HttpStatus.valueOf(response.statusCode)
}
