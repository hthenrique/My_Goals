package br.hthenrique.mygoalskotlin.Model

class RegisterModel: br.hthenrique.mygoalskotlin.Model.User() {
    var password: String? = null
    var confirmPassword: String? = null
}