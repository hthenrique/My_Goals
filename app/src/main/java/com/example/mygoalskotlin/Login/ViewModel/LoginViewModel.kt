package com.example.mygoalskotlin.Login.ViewModel

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Firebase.AuthListener
import com.example.mygoalskotlin.Firebase.UserRepository
import com.example.mygoalskotlin.Login.Model.LoginModel
import com.example.mygoalskotlin.Login.View.LoginActivity
import com.example.mygoalskotlin.Register.View.RegisterActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    private var email:String? = null
    private var password:String? = null

    private val loginModel: LoginModel

    init {
        this.loginModel = LoginModel()
    }

    var authListener: AuthListener? = null
    private val disposables = CompositeDisposable()
    val user by lazy { userRepository.currentUser() }

    fun login(loginModel: LoginModel){
        if (loginModel.email.isEmpty() || loginModel.password.isEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }
        authListener?.onStarted()

        val disposable = userRepository.login(loginModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            },{
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
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