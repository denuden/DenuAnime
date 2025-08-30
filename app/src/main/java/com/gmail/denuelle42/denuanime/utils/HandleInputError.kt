package com.gmail.denuelle42.denuanime.utils

import com.gmail.denuelle42.denuanime.data.remote.error.ErrorData

fun handleInputError(errorData: ErrorData): String {
    val result = StringBuilder()

    errorData.type?.let {
        result.append("type: ${it.joinToString(", ")}\n")
    }

    errorData.rating?.let {
        result.append("rating: ${it.joinToString(", ")}\n")
    }

    return result.toString().trim()
}
