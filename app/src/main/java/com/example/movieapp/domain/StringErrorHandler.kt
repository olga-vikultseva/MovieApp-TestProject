package com.example.movieapp.domain

import com.example.movieapp.R
import com.example.movieapp.util.StringProvider
import java.net.UnknownHostException

/**
 * Common error handler for general exceptions.
 * Scaling: can be extended to a set of feature-specific handlers if needed.
 */
class StringErrorHandler(private val stringProvider: StringProvider) {
    fun handle(error: Throwable): String =
        stringProvider.getString(
            when (error) {
                is UnknownHostException -> R.string.connection_error_message
                else -> R.string.general_error_message
            }
        )
}