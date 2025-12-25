package com.core.data.service.session

import com.core.data.dto.user.UserDto
import com.core.data.dto.user.toDomain
import com.core.data.dto.user.toDto
import com.core.data.util.JsonUnknowKeys
import com.core.domain.client.local_storage.LocalStorageClient
import com.core.domain.model.user.User
import com.core.domain.service.session.SessionService
import com.core.domain.util.Logger

class LocalStorageSessionService(
    private val localStorageClient: LocalStorageClient
) : SessionService {

    private val tag = "LocalStorageSessionService"

    override suspend fun saveSession(token: String, user: User) {
        val userString = JsonUnknowKeys.encodeToString(user.toDto())
        localStorageClient.putString(USER_KEY, userString)
        localStorageClient.putString(TOKEN_KEY, token)
    }

    override suspend fun clearSession() {
        localStorageClient.removeString(USER_KEY)
        localStorageClient.removeString(TOKEN_KEY)
    }

    override suspend fun getUser(): User? {
        val userString = localStorageClient.getString(USER_KEY) ?: return null

        return try {
            val userDto = JsonUnknowKeys.decodeFromString<UserDto>(userString)
            userDto.toDomain()
        } catch (e: Exception) {
            Logger.e(
                tag = tag,
                message = "Failed to parse User: ${e.message}\n${e.stackTraceToString()}"
            )
            null
        }
    }

    override suspend fun getUserId(): String? {
        return getUser()?.id
    }

    override suspend fun getToken(): String? {
        return localStorageClient.getString(TOKEN_KEY)
    }

    companion object {
        private const val USER_KEY = "USER_KEY"
        private const val TOKEN_KEY = "TOKEN_KEY"
    }
}