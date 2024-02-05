package com.example.myinsta.common.validator

import android.text.TextUtils
import com.example.myinsta.R
import com.example.myinsta.common.Constants


class EmailValidator(val email: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid =
            !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        return ValidateResult(
            isValid,
            if (isValid) "Valid" else Constants.EMAIL_INCORRECT
        )
    }
}