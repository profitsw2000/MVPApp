package ru.profitsw2000.mvpapp

import androidx.annotation.WorkerThread

interface LoginApi {
    @WorkerThread
    fun login(userProfile: UserProfile): Boolean

    @WorkerThread
    fun register(userProfile: UserProfile): Boolean

    @WorkerThread
    fun logout(): Boolean

    @WorkerThread
    fun forgotPassword(login: String): Boolean
}