package com.example.mygoalskotlin.Model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.mygoalskotlin.BR
import java.io.Serializable
import java.util.*

open class User: BaseObservable() {

    var uid: String? = null
    var name: String? = null
    var email: String? = null
    var position: String? = null
    var goals: Int? = 0
    var matches: Int? = 0
    var lastUpdate: Int? = 0

    override fun toString(): String {
        return "User(uid='$uid', name=$name, email=$email, position=$position, goals=$goals, matches=$matches, lastUpdate='$lastUpdate')"
    }


}