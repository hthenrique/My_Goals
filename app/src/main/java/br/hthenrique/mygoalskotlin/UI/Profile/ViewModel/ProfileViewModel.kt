package br.hthenrique.mygoalskotlin.UI.Profile.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.database.UserDatabase
import br.hthenrique.mygoalskotlin.Model.repository.RepositoryFirebase
import br.hthenrique.mygoalskotlin.Model.repository.RepositoryLocalDatabase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProfileViewModel(val application: Application): ViewModel() {

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
        val dao = UserDatabase.getDatabase(application).getUserDao()
        repositoryLocal = RepositoryLocalDatabase(dao)


        this.currentUser = getCurrentUser()
        this.currentUid = recoverUid()
        getUserDetailsLiveData()
        getCurrentUserLiveData()
        getErrorLiveData()

    }

    private fun getCurrentUser(): FirebaseUser? {
        return repositoryFirebase!!.getCurrentUser()
    }

    fun updateProfile(user: User?): String {
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

    //Database
    private fun insertIntoDatabase(user: User?){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryLocal?.insertUserDatabase(user)
        }
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


}