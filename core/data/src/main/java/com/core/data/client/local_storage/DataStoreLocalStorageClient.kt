package com.core.data.client.local_storage

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.core.domain.client.local_storage.LocalStorageClient
import kotlinx.coroutines.flow.first
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

class DataStoreLocalStorageClient(
    private val datastore: DataStore<Preferences>
) : LocalStorageClient {

    private val keystore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "app_key_alias"

    override suspend fun putString(key: String, value: String) {
        try {
            datastore.edit { it[stringPreferencesKey(key)] = encryptData(value) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            decryptData(datastore.data.first()[stringPreferencesKey(key)])
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun removeString(key: String) {
        datastore.edit { it.remove(stringPreferencesKey(key)) }
    }

    override suspend fun putLong(key: String, value: Long) {
        datastore.edit { it[longPreferencesKey(key)] = value }
    }

    override suspend fun getLong(key: String, default: Long): Long {
        return datastore.data.first()[longPreferencesKey(key)] ?: default
    }

    override suspend fun removeLong(key: String) {
        datastore.edit { it.remove(longPreferencesKey(key)) }
    }

    override suspend fun putFloat(key: String, value: Float) {
        datastore.edit { it[floatPreferencesKey(key)] = value }
    }

    override suspend fun getFloat(key: String, default: Float): Float {
        return datastore.data.first()[floatPreferencesKey(key)] ?: default
    }

    override suspend fun removeFloat(key: String) {
        datastore.edit { it.remove(floatPreferencesKey(key)) }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        datastore.edit { it[booleanPreferencesKey(key)] = value }
    }

    override suspend fun getBoolean(key: String, default: Boolean): Boolean {
        return datastore.data.first()[booleanPreferencesKey(key)] ?: default
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return datastore.data.first()[booleanPreferencesKey(key)]
    }

    override suspend fun removeBoolean(key: String) {
        datastore.edit { it.remove(booleanPreferencesKey(key)) }
    }

    override suspend fun putDouble(key: String, value: Double) {
        datastore.edit { it[doublePreferencesKey(key)] = value }
    }

    override suspend fun getDouble(key: String, default: Double): Double {
        return datastore.data.first()[doublePreferencesKey(key)] ?: default
    }

    override suspend fun removeDouble(key: String) {
        datastore.edit { it.remove(doublePreferencesKey(key)) }
    }

    override suspend fun putInt(key: String, value: Int) {
        datastore.edit { it[intPreferencesKey(key)] = value }
    }

    override suspend fun getInt(key: String, default: Int): Int {
        return datastore.data.first()[intPreferencesKey(key)] ?: default
    }

    override suspend fun removeInt(key: String) {
        datastore.edit { it.remove(intPreferencesKey(key)) }
    }

    override suspend fun removeKeysStartedWith(key: String) {
        datastore.edit { preferences ->
            val keys = preferences.asMap()
                .filterKeys { it.name.startsWith(key) }
                .keys

            keys.forEach { preferences.remove(it) }
        }
    }

    override suspend fun getKeysStartedWith(key: String): List<String> {
        var keys = emptyList<String>()
        datastore.edit { preferences ->
            keys = preferences.asMap()
                .filterKeys { it.name.startsWith(key) }
                .keys
                .map { it.name }
        }

        return keys
    }

    private fun getKey(): Key {
        if (keystore.containsAlias(keyAlias)) {
            return keystore.getKey(keyAlias, null) as Key
        } else {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.apply {
                init(
                    KeyGenParameterSpec.Builder(
                        keyAlias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .build()
                )
            }
            return keyGenerator.generateKey()
        }
    }

    private fun encryptData(data: String): String {
        val key = getKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.iv
        val encryption = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        return Base64.encodeToString(encryption, Base64.DEFAULT) + ":" + Base64.encodeToString(
            iv,
            Base64.DEFAULT
        )
    }

    private fun decryptData(encryptedData: String?): String? {
        if (encryptedData == null) return null

        val dataParts = encryptedData.split(":")
        val encryptedBytes = Base64.decode(dataParts[0], Base64.DEFAULT)
        val iv = Base64.decode(dataParts[1], Base64.DEFAULT)

        val key = getKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmParameterSpec = GCMParameterSpec(128, iv)

        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}