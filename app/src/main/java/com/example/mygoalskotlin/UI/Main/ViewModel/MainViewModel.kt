package com.example.mygoalskotlin.UI.Main.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygoalskotlin.Model.User
import com.example.mygoalskotlin.Model.database.UserDatabase
import com.example.mygoalskotlin.Model.repository.RepositoryFirebase
import com.example.mygoalskotlin.Model.repository.RepositoryLocalDatabase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(val application: Application): ViewModel() {

    private var repositoryFirebase: RepositoryFirebase? = null
    private var repositoryLocal: RepositoryLocalDatabase? = null

    private var saveUserDetailsFirebase: Boolean? = null
    private var currentUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var userDetailsLiveData: MutableLiveData<User>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null
    private var currentUser: FirebaseUser? = null
    private var currentUid: String? = null

    init {
        repositoryFirebase = RepositoryFirebase()
        this.currentUser = getCurrentUser()
        this.currentUid = recoverUid()
        getUserDetailsLiveData()
        getCurrentUserLiveData()
        getErrorLiveData()

        val dao = UserDatabase.getDatabase(application).getUserDao()
        repositoryLocal = RepositoryLocalDatabase(dao)
    }

    private fun getCurrentUser(): FirebaseUser? {
        return repositoryFirebase!!.getCurrentUser()
    }

    fun getUserDetails(): User {
        var userDetails = repositoryFirebase!!.getUserDetailsFromFirebase(currentUser?.uid!!)
        if (userDetails != null){
            return userDetails
        }else{
            userDetails = User()

        }
        return userDetails
    }

    fun updateUser(){

        repositoryFirebase!!.updateUserFirebase()
    }


    fun saveDetails(user: User?): String{
        val detailsHasUpdated: String

        user?.lastUpdate = getCurrentDate()

        insertIntoDatabase(user)
        saveUserDetailsFirebase = repositoryFirebase!!.saveUserDetailsFirebase(user)
        if (saveUserDetailsFirebase as Boolean){
            detailsHasUpdated = "Success"
        }else{
            detailsHasUpdated = errorMutableLiveData.toString()
        }
        return detailsHasUpdated
    }

    fun deleteUser(user: User?){
        deleteIntoDatabase(user)
    }

    //Database
    private fun insertIntoDatabase(user: User?){
        viewModelScope.launch {
            repositoryLocal?.insertUserDatabase(user)
        }
    }

    private fun deleteIntoDatabase(user: User?){
        viewModelScope.launch {
            repositoryLocal?.deleteUserDatabase(user)
        }
    }

    private fun getIntoDatabase(uid: String?){

    }

    //Firebase
    fun getUserDetailsLiveData(): MutableLiveData<User>? {
        userDetailsLiveData = repositoryFirebase!!.getUserDetailsLiveData(currentUser?.uid)
        return userDetailsLiveData
    }

    fun getCurrentUserLiveData(): MutableLiveData<FirebaseUser>? {
        currentUserMutableLiveData = repositoryFirebase!!.getMutableLiveData()
        return currentUserMutableLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>? {
        errorMutableLiveData = repositoryFirebase!!.getErrorMutableLiveData()
        return errorMutableLiveData
    }

    @SuppressLint("CommitPrefEdits")
    private fun recoverUid(): String? {
        val uid: String? = null
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        sharedPreferences.apply {
            getString("uid", uid)
        }
        return uid
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