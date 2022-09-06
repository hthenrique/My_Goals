package br.hthenrique.mygoalskotlin.Model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import br.hthenrique.mygoalskotlin.Model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: br.hthenrique.mygoalskotlin.Model.User?)

    @Update
    suspend fun updateUser(user: br.hthenrique.mygoalskotlin.Model.User?)

    @Delete
    suspend fun deleteUser(user: br.hthenrique.mygoalskotlin.Model.User?)

    @Query("SELECT * FROM user WHERE :uid")
    fun getUser(uid: String?): Array<br.hthenrique.mygoalskotlin.Model.User>?

}