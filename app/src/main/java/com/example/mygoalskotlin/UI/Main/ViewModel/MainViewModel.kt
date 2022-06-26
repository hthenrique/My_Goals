package com.example.mygoalskotlin.UI.Main.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygoalskotlin.Model.User
import com.example.mygoalskotlin.Model.repository.Repository
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel: ViewModel() {
    private var repository: Repository? = null
    private var saveUserDetailsFirebase: Boolean? = null
    private var currentUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var userDetailsLiveData: MutableLiveData<User>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null
    private var currentUser: FirebaseUser? = null

    init {
        repository = Repository()
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

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun getCurrentDate(): String {
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        return dateFormat.format(LocalDateTime.now())
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun compareDates(firebaseDate: String, databaseDate: String){
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val firebase: LocalDateTime = LocalDateTime.parse(firebaseDate, dateFormat)
        val database: LocalDateTime = LocalDateTime.parse(databaseDate, dateFormat)

        when{
            firebase > database -> {
                Log.i("Compare Dates", "Firebase is the new")
            }
            firebase < database -> {
                Log.i("Compare Dates", "Database is the new")
            }
        }

    }


}