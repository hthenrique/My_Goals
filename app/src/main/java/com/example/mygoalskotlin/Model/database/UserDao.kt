package com.example.mygoalskotlin.Model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mygoalskotlin.Model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User?)

    @Update
    suspend fun updateUser(user: User?)

    @Query("SELECT * FROM user WHERE :uid")
    fun getUser(uid: String?): Array<User>?

}