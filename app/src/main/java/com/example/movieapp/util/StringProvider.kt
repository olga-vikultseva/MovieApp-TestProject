package com.example.movieapp.util

import android.content.Context

class StringProvider(private val context: Context) {
    fun getString(resId: Int): String =
        context.getString(resId)

    fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)
}