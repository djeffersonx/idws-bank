package br.com.idws.bank.accountvalidator.application

import br.com.idws.bank.accountvalidator.domain.model.account.Account
import br.com.idws.bank.accountvalidator.domain.service.AccountValidatorService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/validation")
class ValidationController(val accountValidatorService: AccountValidatorService) {

    val logger = LoggerFactory.getLogger(ValidationController::class.java)

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody account: Account) {
        logger.debug("Starting ValidationController.create")
        accountValidatorService.validateToCreate(account)
    }

}