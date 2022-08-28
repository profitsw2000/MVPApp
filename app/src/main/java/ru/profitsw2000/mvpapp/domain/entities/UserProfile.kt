package ru.profitsw2000.mvpapp.domain.entities

data class UserProfile(
    val id: String,
    val login: String,
    val password: String,
    val email: String
)
