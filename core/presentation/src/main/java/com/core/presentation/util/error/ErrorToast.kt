package com.core.presentation.util.error

import android.content.Context
import android.widget.Toast
import com.core.presentation.R

fun errorToast(
    errorMessage: String?,
    context: Context,
    generalError: String = context.getString(R.string.error_unknown)
) {
    val message = errorMessage ?: generalError

    Toast.makeText(
        context, message, Toast.LENGTH_SHORT
    ).show()
}