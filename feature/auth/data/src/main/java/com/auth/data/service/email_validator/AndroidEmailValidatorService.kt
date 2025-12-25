package com.auth.data.service.email_validator

import android.util.Patterns
import com.auth.domain.service.email_validator.EmailValidatorService

class AndroidEmailValidatorService : EmailValidatorService {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}