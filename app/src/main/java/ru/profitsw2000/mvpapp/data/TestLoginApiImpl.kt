package ru.profitsw2000.mvpapp.data

import ru.profitsw2000.mvpapp.domain.LoginApi
import ru.profitsw2000.mvpapp.domain.entities.UserProfile

class TestLoginApiImpl : LoginApi {

    val users: MutableList<UserProfile> = mutableListOf(UserProfile("admin","1234"), UserProfile("user","0000"))

    override fun login(login: String, password: String): Boolean {
        for(user in users){
            Thread.sleep(3_000)
            if(login.equals(user.login,true) && password.equals(user.password,true))
                return true
        }
        return false
    }

    override fun register(login: String, password: String): Boolean {
        Thread.sleep(3_000)
        for(user in users){
            if(login.equals(user.login,true))
                return false
        }
        users.add(UserProfile(login, password))
        return true
    }

    override fun logout(): Boolean {
        Thread.sleep(2_000)
        return true
    }

    override fun restorePassword(login: String): Boolean {
        Thread.sleep(3_000)
        for(user in users){
            if(login.equals(user.login,true))
                return true
        }
        return false
    }

    override fun changePassword(password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun changeEmail(login: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAccount(login: String): Boolean {
        TODO("Not yet implemented")
    }
}