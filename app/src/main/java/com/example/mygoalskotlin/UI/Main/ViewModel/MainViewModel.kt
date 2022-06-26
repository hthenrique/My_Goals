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

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null
    private var saveUserDetailsFirebase: Boolean? = null
    private var currentUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var userDetailsLiveData: MutableLiveData<User>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null
    private var currentUser: FirebaseUser? = null

    init {
        repository = Repository(application)
        this.currentUser = getCurrentUser()
        getUserDetailsLiveData()
        getCurrentUserLiveData()
        getErrorLiveData()
    }

    private fun getCurrentUser(): FirebaseUser? {
        return repository!!.getCurrentUser()
    }

    fun getUserDetails(): User {
        var userDetails = repository!!.getUserDetailsFromFirebase(currentUser?.uid!!)
        if (userDetails != null){
            return userDetails
        }else{
            userDetails = User()

        }
        return userDetails
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
        userDetailsLiveData = repository!!.getUserDetailsLiveData(currentUser?.uid!!)
        return userDetailsLiveData
    }

    fun getCurrentUserLiveData(): MutableLiveData<FirebaseUser>? {
        currentUserMutableLiveData = repository!!.getMutableLiveData()
        return currentUserMutableLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>? {
        errorMutableLiveData = repository!!.getErrorMutableLiveData()
        return errorMutableLiveData
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return dateFormat.format(Calendar.getInstance().time)
    }


}