package ru.profitsw2000.mvpapp.domain

import androidx.annotation.WorkerThread

interface LoginApi {
    @WorkerThread
    fun login(login: String, password: String): Boolean

    @WorkerThread
    fun register(login: String, password: String): Boolean

    @WorkerThread
    fun restorePassword(login: String): Boolean

    @WorkerThread
    fun changePassword(password: String): Boolean

    @WorkerThread
    fun changeEmail(login: String): Boolean

    @WorkerThread
    fun removeAccount(login: String): Boolean

    @WorkerThread
    fun logout(): Boolean
}