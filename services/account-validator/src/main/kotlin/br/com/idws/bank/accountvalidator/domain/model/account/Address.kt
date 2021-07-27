package br.com.idws.bank.accountvalidator.domain.model.account

class Address(
    val street: String,
    val number: String,
    val neighborhood: String,
    val code: String,
    val city: String,
    val state: String,
    val country: String
)