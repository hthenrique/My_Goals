package com.example.mygoalskotlin.UI.Main.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mygoalskotlin.Model.User
import com.example.mygoalskotlin.Model.repository.Repository
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: AndroidViewModel {
    private var repository: Repository? = null
    private var saveUserDetailsFirebase: Boolean? = null
    private var currentUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var userDetailsLiveData: MutableLiveData<User>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null

    constructor(application: Application) : super(application){
        repository = Repository(application)
        getLoggedUser()
        userDetailsLiveData = repository!!.getUserDetailsLiveData(getLoggedUser()?.uid!!)
        currentUserMutableLiveData = repository!!.getMutableLiveData()
        errorMutableLiveData = repository!!.getErrorMutableLiveData()
    }

    fun getLoggedUser(): FirebaseUser? {
        return repository!!.getLoggedUser()
    }

    fun getUserDetails(uid: String): User {
        return repository!!.getUserDetailsFromFirebase(uid)
    }

    fun updateUser(){
        repository!!.updateUserFirebase()
    }


    fun saveDetails(user: User?): String{
        val detailsHasUpdated: String

        user?.lastUpdate = getCurrentDate()

        saveUserDetailsFirebase = repository!!.saveUserDetailsFirebase(user!!)
        if (saveUserDetailsFirebase as Boolean){
            detailsHasUpdated = "Success"
        }else{
            detailsHasUpdated = errorMutableLiveData.toString()
        }
        return detailsHasUpdated
    }

    fun getUserDetailsLiveData(): MutableLiveData<User>? {
        return userDetailsLiveData
    }

    fun getLoggedUserLiveData(): MutableLiveData<FirebaseUser>? {
        return currentUserMutableLiveData
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        return dateFormat.format(Calendar.getInstance().time).toString()
    }


}