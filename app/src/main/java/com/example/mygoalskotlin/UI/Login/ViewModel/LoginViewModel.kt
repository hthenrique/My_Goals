package com.example.mygoalskotlin.UI.Login.ViewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Firebase.AuthListener
import com.example.mygoalskotlin.Model.LoginModel
import com.example.mygoalskotlin.UI.Register.View.RegisterActivity
import io.reactivex.disposables.CompositeDisposable


class LoginViewModel(): ViewModel() {

    private var email:String? = null
    private var password:String? = null

    private val loginModel: LoginModel

    init {
        this.loginModel = LoginModel()
    }

    var authListener: AuthListener? = null
    private val disposables = CompositeDisposable()

    fun login(loginModel: LoginModel){
        if (loginModel.email!!.isEmpty() || loginModel.password!!.isEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }
        authListener?.onStarted()

    }

    fun goToSignup(view: View){
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}