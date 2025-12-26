package com.core.domain.model.user

data class User(
    val id: String,
    val username: String,
    val email: String
)

val dummyUser = User(
    id = "1",
    username = "Alex",
    email = "alex@email.com"
)