package br.com.idws.bank.accountvalidator.domain.model.account

import java.time.LocalDate

class Person(
    val fullName: String,
    val birthDate: LocalDate,
    documentNumber: String,
    val address: Address
) {
    var documentNumber: String = documentNumber
        set(value) {
            field = value.replace("[^0-9]".toRegex(), "")
        }
}