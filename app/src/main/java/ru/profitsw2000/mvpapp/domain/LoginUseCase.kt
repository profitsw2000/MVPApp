package ru.profitsw2000.mvpapp.domain

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

interface LoginUseCase {
    @WorkerThread
    fun login(login: String,
              password: String,
              @MainThread callback: (Boolean) -> Unit)

    @WorkerThread
    fun register(login: String,
                 password: String,
                 email: String,
                 @MainThread callback: (Boolean) -> Unit)

    @WorkerThread
    fun restorePassword(email: String,
                        @MainThread callback: (Boolean) -> Unit)

    @WorkerThread
    fun logout(): Boolean
}