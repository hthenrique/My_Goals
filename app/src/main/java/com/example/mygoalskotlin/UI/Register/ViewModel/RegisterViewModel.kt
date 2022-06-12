package com.example.mygoalskotlin.UI.Register.ViewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Firebase.AuthListener
import com.example.mygoalskotlin.UI.Login.View.LoginActivity
import com.example.mygoalskotlin.Model.RegisterModel
import io.reactivex.disposables.CompositeDisposable

class RegisterViewModel(): ViewModel() {

    private var email:String? = null
    private var password:String? = null

    private val registerModel: RegisterModel

    init {
        this.registerModel = RegisterModel()
    }
    var authListener: AuthListener? = null
    private val disposables = CompositeDisposable()

    fun signup(registerModel: RegisterModel){
        registerModel.email = email.toString()
        registerModel.password = password.toString()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }
        authListener?.onStarted()

    }

    fun goToLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}