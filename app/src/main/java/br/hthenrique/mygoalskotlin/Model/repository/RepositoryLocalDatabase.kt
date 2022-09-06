package br.hthenrique.mygoalskotlin.Model.repository

import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.database.UserDao

class RepositoryLocalDatabase(val userDao: UserDao) {

    suspend fun insertUserDatabase(user: User?) = userDao.insert(user)
    suspend fun updateUserDatabase(user: User?) = userDao.updateUser(user)
    suspend fun deleteUserDatabase(user: User?) = userDao.deleteUser(user)
    fun getUserDatabase(uid: String?) = userDao.getUser(uid)

}