package ru.sber.rdbms.exception

class TransactionFailed(message: String, cause: Throwable) : Exception(message, cause)