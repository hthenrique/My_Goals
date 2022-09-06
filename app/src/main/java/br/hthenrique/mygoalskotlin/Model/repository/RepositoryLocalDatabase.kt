package br.hthenrique.mygoalskotlin.Model.repository

import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.database.UserDao

class RepositoryLocalDatabase(val userDao: br.hthenrique.mygoalskotlin.Model.database.UserDao) {

    suspend fun insertUserDatabase(user: br.hthenrique.mygoalskotlin.Model.User?) = userDao.insert(user)
    suspend fun updateUserDatabase(user: br.hthenrique.mygoalskotlin.Model.User?) = userDao.updateUser(user)
    suspend fun deleteUserDatabase(user: br.hthenrique.mygoalskotlin.Model.User?) = userDao.deleteUser(user)
    fun getUserDatabase(uid: String?) = userDao.getUser(uid)

}