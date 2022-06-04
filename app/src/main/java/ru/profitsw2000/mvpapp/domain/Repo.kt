package ru.profitsw2000.mvpapp.domain

import ru.profitsw2000.mvpapp.domain.entities.UserProfile

interface Repo {
    // Create
    fun addUser(user: UserProfile)

    // Read
    fun getAllUsers(): List<UserProfile>

    // Update
    fun changeUser(id: String, user: UserProfile)

    // Delete
    fun deleteUser(id: String)
    //fun deleteAll()
}