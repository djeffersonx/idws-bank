package br.com.idws.bank.accountvalidator.infrastructure.fuel

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

class ApiException(val errors: List<String>) : Exception() {
    companion object {
        fun of(key: String, message: String) {
            throw ApiException(listOf(key, message))
        }

        fun of(request: Request, response: Response, result: Result<String, FuelError>) {
            val (_, error) = result
            of("InternalError on call: ${request.url}", error?.localizedMessage ?: error.toString())
        }

    }
}