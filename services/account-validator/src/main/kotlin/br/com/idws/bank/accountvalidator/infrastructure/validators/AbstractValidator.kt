package br.com.idws.bank.accountvalidator.infrastructure.validators

import br.com.idws.bank.accountvalidator.domain.AccountCreateValidator
import br.com.idws.bank.accountvalidator.domain.model.account.Account
import br.com.idws.bank.accountvalidator.domain.model.validation.Error
import br.com.idws.bank.accountvalidator.infrastructure.fuel.getOrError
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.objectBody

open class AbstractValidator(
    val url: String,
    val mapper: ObjectMapper
) : AccountCreateValidator {

    override fun validate(account: Account): List<Error> =
        Fuel.post(url)
            .objectBody(account, mapper = mapper)
            .getOrError { _, _, _ -> emptyList<Error>() } as List<Error>

}

