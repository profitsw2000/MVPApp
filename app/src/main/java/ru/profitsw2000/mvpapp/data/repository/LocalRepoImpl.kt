package ru.profitsw2000.mvpapp.data.repository

import ru.profitsw2000.mvpapp.domain.Repo
import ru.profitsw2000.mvpapp.domain.entities.UserProfile

class LocalRepoImpl: Repo {

    val users: MutableList<UserProfile> =
        mutableListOf(UserProfile("0","admin","1234", "admin@mvp.ru"),
            UserProfile("1","user","0000","user@mvp.ru"))

    override fun addUser(user: UserProfile) {
        users.add(user)
    }

    override fun getAllUsers(): List<UserProfile> {
        return users
    }

    override fun changeUser(id: String, user: UserProfile) {
        for (myUser in users) {
            if (myUser.id.equals(user.id)) {
                val index = users.indexOf(myUser)
                users.set(index, user)
            }
        }
    }

    override fun deleteUser(id: String) {
        for (myUser in users) {
            if (myUser.id.equals(id)) {
                val index = users.indexOf(myUser)
                users.removeAt(index)
            }
        }
    }

    override fun deleteAll() {
        users.clear()
    }
}