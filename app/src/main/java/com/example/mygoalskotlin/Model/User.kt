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

    @get:Bindable
    var goals: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    @get:Bindable
    var matches: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    var lastUpdate: String = ""

    override fun toString(): String {
        return "User(uid='$uid', name=$name, email=$email, position=$position, goals=$goals, matches=$matches, lastUpdate='$lastUpdate')"
    }


}