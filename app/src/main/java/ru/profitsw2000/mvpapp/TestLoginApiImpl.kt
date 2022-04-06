package ru.profitsw2000.mvpapp

class TestLoginApiImpl : LoginApi {

    val users: MutableList<UserProfile> = mutableListOf(UserProfile("admin","1234"), UserProfile("user","0000"))

    override fun login(login: String, password: String): Boolean {
        for(user in users){
            if(login.equals(user.login,true) && password.equals(user.password,true))
                return true
        }
        return false
    }

    override fun register(login: String, password: String): Boolean {
        Thread.sleep(2_000)

        return login.isNotEmpty()
    }

    override fun logout(): Boolean {
        Thread.sleep(2_000)
        return true
    }

    override fun restorePassword(login: String): Boolean {
        for(user in users){
            if(login.equals(user.login,true))
                return true
        }
        return false
    }
}