package com.core.domain.util

interface LoggingStrategy {
    fun d(tag: String, message: String)
    fun d(message: String)

    fun i(tag: String, message: String)
    fun i(message: String)

    fun w(tag: String, message: String)
    fun w(message: String)

    fun e(tag: String, message: String)
    fun e(message: String)
}

object LoggerBuilder {
    fun setLoggingStrategy(loggingStrategy: LoggingStrategy) {
        Logger.setLoggingStrategy(loggingStrategy)
    }
}

object Logger : LoggingStrategy {
    private var impl: LoggingStrategy? = null

    internal fun setLoggingStrategy(loggingStrategy: LoggingStrategy) {
        impl = loggingStrategy
    }

    override fun d(tag: String, message: String) {
        impl?.d(tag, message)
    }

    override fun d(message: String) {
        impl?.d(message)
    }

    override fun i(tag: String, message: String) {
        impl?.i(tag, message)
    }

    override fun i(message: String) {
        impl?.i(message)
    }

    override fun w(tag: String, message: String) {
        impl?.w(tag, message)
    }

    override fun w(message: String) {
        impl?.w(message)
    }

    override fun e(tag: String, message: String) {
        impl?.e(tag, message)
    }

    override fun e(message: String) {
        impl?.e(message)
    }
}