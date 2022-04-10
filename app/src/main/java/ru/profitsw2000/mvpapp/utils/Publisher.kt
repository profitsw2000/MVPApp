package ru.profitsw2000.mvpapp.utils

import android.os.Handler

class Publisher<T> () {

    private val subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()
    var value: T? = null
        private set

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
    }

    fun unsubscribeAll() {
        subscribers.clear()
    }

    fun post(value: T) {
        subscribers.forEach {
            it.invoke(value)
        }
    }
}

private data class Subscriber<T>(
    private val handler: Handler,
    private val callback: (T?) -> Unit
) {
    fun invoke(value: T?) {
        handler.post {
            callback.invoke(value)
        }
    }
}