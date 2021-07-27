package br.com.idws.bank.accountvalidator.domain.model.validation

class ValidationException(val errors: List<Error>) : Exception()