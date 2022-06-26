package com.example.mygoalskotlin.UI.Login.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Model.LoginModel
import com.example.mygoalskotlin.Model.repository.Repository
import com.example.mygoalskotlin.Utils.Resource
import com.google.firebase.auth.FirebaseUser


class LoginViewModel : ViewModel() {
    private var repository: Repository? = null
    private var LoginUserLiveData: MutableLiveData<FirebaseUser>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null

    init {
        repository = Repository()
        LoginUserLiveData = repository!!.getMutableLiveData()
        errorMutableLiveData = repository!!.getErrorMutableLiveData()
    }

    fun login(loginModel: LoginModel){
        repository?.loginExistentUser(loginModel)
        if (LoginUserLiveData != null) {
            Resource.Success(LoginUserLiveData)
        }
        Resource.Error("Login Failed", null)
    }

    fun getLoginMutableLiveData(): MutableLiveData<FirebaseUser>?{
        return LoginUserLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>?{
        return errorMutableLiveData
    }
}