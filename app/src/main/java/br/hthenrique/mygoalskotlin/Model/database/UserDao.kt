package br.hthenrique.mygoalskotlin.Model.database

import androidx.room.*
import br.hthenrique.mygoalskotlin.Model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?)

    @Update
    fun updateUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @Query("SELECT * FROM user WHERE :uid")
    fun getUser(uid: String?): Array<User>?

}