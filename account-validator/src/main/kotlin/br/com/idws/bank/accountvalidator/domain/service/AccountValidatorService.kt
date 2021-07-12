package br.com.idws.bank.accountvalidator.domain.service

import br.com.idws.bank.accountvalidator.domain.AccountCreateValidator
import br.com.idws.bank.accountvalidator.domain.model.account.Account
import br.com.idws.bank.accountvalidator.domain.model.validation.ValidationException
import org.springframework.stereotype.Service

@Service
class AccountValidatorService(
    val accountCreateValidators: List<AccountCreateValidator>
) {

    fun validateToCreate(account: Account) {
        accountCreateValidators.map { it.validate(account) }
            .flatten()
            .takeIf { it.isNotEmpty() }
            ?.let { throw ValidationException(it) }
    }

}