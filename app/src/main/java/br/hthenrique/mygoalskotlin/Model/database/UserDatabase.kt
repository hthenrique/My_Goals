package br.hthenrique.mygoalskotlin.Model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.hthenrique.mygoalskotlin.Model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        private const val DB_NAME = "user_database"

        fun getDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                UserDatabase.Companion.DB_NAME
            ).build()
        }
    }
}