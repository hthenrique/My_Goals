package br.hthenrique.mygoalskotlin.Model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.hthenrique.mygoalskotlin.Model.User

@Database(
    entities = [br.hthenrique.mygoalskotlin.Model.User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): br.hthenrique.mygoalskotlin.Model.database.UserDao

    companion object {
        @Volatile
        private var INSTANCE: br.hthenrique.mygoalskotlin.Model.database.UserDatabase? = null
        private const val DB_NAME = "user_database"

        fun getDatabase(context: Context): br.hthenrique.mygoalskotlin.Model.database.UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                br.hthenrique.mygoalskotlin.Model.database.UserDatabase::class.java,
                br.hthenrique.mygoalskotlin.Model.database.UserDatabase.Companion.DB_NAME
            ).build()
        }
    }
}