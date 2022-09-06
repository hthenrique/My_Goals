package br.hthenrique.mygoalskotlin.Model

import androidx.annotation.NonNull
import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
open class User: BaseObservable(), Serializable {


    @PrimaryKey @NonNull @ColumnInfo(name = "uid") var uid: String = ""
    @ColumnInfo(name = "name") var name: String? = null
    @ColumnInfo(name = "email") var email: String? = null
    @ColumnInfo(name = "position") var position: String? = null
    @ColumnInfo(name = "goals") var goals: Int? = 0
    @ColumnInfo(name = "matches") var matches: Int? = 0
    @ColumnInfo(name = "lastUpdate") var lastUpdate: String? = null

    override fun toString(): String {
        return "User(uid='$uid', name=$name, email=$email, position=$position, goals=$goals, matches=$matches, lastUpdate='$lastUpdate')"
    }


}