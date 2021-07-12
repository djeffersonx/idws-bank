package br.com.idws.bank.accountvalidator.domain

import br.com.idws.bank.accountvalidator.domain.model.account.Account
import br.com.idws.bank.accountvalidator.domain.model.validation.Error

interface AccountCreateValidator {

    fun validate(account: Account): List<Error>

}