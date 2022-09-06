package br.hthenrique.mygoalskotlin.Firebase

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}