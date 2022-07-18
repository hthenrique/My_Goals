package com.example.mygoalskotlin.Model.repository

import com.example.mygoalskotlin.Model.User
import com.example.mygoalskotlin.Model.database.UserDao

class RepositoryLocalDatabase(val userDao: UserDao) {

    suspend fun insertUserDatabase(user: User?) = userDao.insert(user)
    suspend fun updateUserDatabase(user: User?) = userDao.updateUser(user)
    fun getUserDatabase(uid: String?) = userDao.getUser(uid)

}