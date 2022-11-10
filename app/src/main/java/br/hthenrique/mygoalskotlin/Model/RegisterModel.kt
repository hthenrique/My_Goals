package br.hthenrique.mygoalskotlin.Model

import androidx.room.ColumnInfo

class RegisterModel {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
}