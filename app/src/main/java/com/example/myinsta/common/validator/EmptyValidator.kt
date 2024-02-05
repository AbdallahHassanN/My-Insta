package com.example.myinsta.common.validator

import com.example.myinsta.common.Constants


class EmptyValidator(private val input: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid = input.isNotEmpty()
        return ValidateResult(
            isValid,
            if (isValid) "valid" else Constants.EMPTY_FIELD
        )
    }
}