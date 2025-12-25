package com.core.domain.client.local_storage

interface LocalStorageClient {
    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

    suspend fun removeString(key: String)

    suspend fun putLong(key: String, value: Long)

    suspend fun getLong(key: String, default: Long): Long

    suspend fun removeLong(key: String)

    suspend fun putFloat(key: String, value: Float)

    suspend fun getFloat(key: String, default: Float): Float

    suspend fun removeFloat(key: String)

    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun getBoolean(key: String, default: Boolean): Boolean

    suspend fun getBoolean(key: String): Boolean?

    suspend fun removeBoolean(key: String)

    suspend fun putDouble(key: String, value: Double)

    suspend fun getDouble(key: String, default: Double): Double

    suspend fun removeDouble(key: String)

    suspend fun putInt(key: String, value: Int)

    suspend fun getInt(key: String, default: Int): Int

    suspend fun removeInt(key: String)

    suspend fun getKeysStartedWith(key: String): List<String>

    suspend fun removeKeysStartedWith(key: String)
}