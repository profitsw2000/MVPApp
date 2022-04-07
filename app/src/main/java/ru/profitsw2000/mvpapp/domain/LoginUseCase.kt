package ru.profitsw2000.mvpapp.domain

import androidx.annotation.WorkerThread

interface LoginUseCase {
    @WorkerThread
    fun login(login: String, password: String): Boolean

    @WorkerThread
    fun register(login: String, password: String): Boolean

    @WorkerThread
    fun restorePassword(login: String): Boolean

    @WorkerThread
    fun logout(): Boolean
}