package ru.profitsw2000.mvpapp.data

import ru.profitsw2000.mvpapp.data.repository.LocalRepoImpl
import ru.profitsw2000.mvpapp.domain.LoginApi
import ru.profitsw2000.mvpapp.domain.entities.UserProfile

class TestLoginApiImpl : LoginApi {

    private val localRepo = LocalRepoImpl()

    override fun login(login: String, password: String): Boolean {
        val userList = localRepo.getAllUsers()

        Thread.sleep(3_000)
        for(user in userList){
            if(login.equals(user.login,true) && password.equals(user.password,true))
                return true
        }
        return false
    }

    override fun register(login: String, password: String): Boolean {
        val userList = localRepo.getAllUsers()

        Thread.sleep(3_000)
        for(user in userList){
            if(login.equals(user.login,true))
                return false
        }
        localRepo.addUser(UserProfile(userList.size.toString(),login, password, "user@mvp.ru"))
        return true
    }

    override fun logout(): Boolean {
        Thread.sleep(2_000)
        return true
    }

    override fun restorePassword(login: String): Boolean {
        val userList = localRepo.getAllUsers()

        Thread.sleep(3_000)
        for(user in userList){
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