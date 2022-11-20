package br.hthenrique.mygoalskotlin.Model.repository

import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.database.UserDao

class RepositoryLocalDatabase(val userDao: UserDao) {

    fun insertUserDatabase(user: User?) = userDao.insert(user)
    fun updateUserDatabase(user: User?) = userDao.updateUser(user)
    fun deleteUserDatabase(user: User?) = userDao.deleteUser(user)
    fun getUserDatabase(uid: String?) = userDao.getUser(uid)

}