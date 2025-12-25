package com.core.domain.service.session

import com.core.domain.model.user.User

interface SessionService {
    suspend fun saveSession(token: String, user: User)
    suspend fun clearSession()
    suspend fun getUser(): User?
    suspend fun getUserId(): String?
    suspend fun getToken(): String?
}