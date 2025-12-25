package com.core.data.util

import android.util.Log
import com.core.domain.util.LoggingStrategy

class AndroidLoggingStrategy : LoggingStrategy {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun d(message: String) {
        Log.d("", message)
    }


    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun i(message: String) {
        Log.i("", message)
    }


    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun w(message: String) {
        Log.w("", message)
    }


    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun e(message: String) {
        Log.e("", message)
    }
}