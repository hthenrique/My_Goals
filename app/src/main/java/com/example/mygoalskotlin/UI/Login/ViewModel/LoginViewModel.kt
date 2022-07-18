package com.example.mygoalskotlin.UI.Login.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Model.LoginModel
import com.example.mygoalskotlin.Model.repository.RepositoryFirebase
import com.example.mygoalskotlin.Utils.Resource
import com.google.firebase.auth.FirebaseUser


class LoginViewModel : ViewModel() {
    private var repositoryFirebase: RepositoryFirebase? = null
    private var loginUserLiveData: MutableLiveData<FirebaseUser>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null

    init {
        repositoryFirebase = RepositoryFirebase()
        loginUserLiveData = repositoryFirebase!!.getMutableLiveData()
        errorMutableLiveData = repositoryFirebase!!.getErrorMutableLiveData()
    }

    fun login(loginModel: LoginModel){
        repositoryFirebase?.loginExistentUser(loginModel)
        if (loginUserLiveData != null) {
            Resource.Success(loginUserLiveData)
        }
        Resource.Error("Login Failed", null)
    }

    fun getLoginMutableLiveData(): MutableLiveData<FirebaseUser>?{
        return loginUserLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>?{
        return errorMutableLiveData
    }
}