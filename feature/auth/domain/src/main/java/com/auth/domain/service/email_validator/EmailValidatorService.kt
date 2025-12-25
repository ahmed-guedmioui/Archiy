package com.auth.domain.service.email_validator

interface EmailValidatorService {
    fun isValid(email: String): Boolean
}