package br.hthenrique.mygoalskotlin.Model

class RegisterModel: User() {
    var password: String? = null
    var confirmPassword: String? = null
}