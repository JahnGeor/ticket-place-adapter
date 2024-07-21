package ru.kidesoft.ticketplace.adapter.domain.exception

class DbException(message : String) : Exception(message) {

}

class WebException() : Exception() {
    var statusCode = 0
    override var cause : Throwable? = null
    override var message : String? = null
}

class ValidationException : Exception() {

}