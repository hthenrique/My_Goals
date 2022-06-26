package com.example.mygoalskotlin.UI.Register.ViewModel

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Firebase.AuthListener
import com.example.mygoalskotlin.UI.Login.View.LoginActivity
import com.example.mygoalskotlin.Model.RegisterModel
import com.example.mygoalskotlin.Model.User
import com.example.mygoalskotlin.Model.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable

class RegisterViewModel : ViewModel() {

    private var repository: Repository? = null
    private var mutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null

    init {
        repository = Repository()
        mutableLiveData = repository!!.getMutableLiveData()
        errorMutableLiveData = repository!!.getErrorMutableLiveData()
    }

    fun saveUserDetails() {
        var user: User = User()
        user.uid = mutableLiveData?.value?.uid
        user.email = mutableLiveData?.value?.email
        repository!!.saveUserDetailsFirebase(user)
    }


    fun signup(registerModel: RegisterModel){
        repository?.registerNewUser(registerModel)
    }

    fun getMutableLiveData(): MutableLiveData<FirebaseUser>? {
        return mutableLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>?{
        return errorMutableLiveData
    }
}