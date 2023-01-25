package br.hthenrique.mygoalskotlin.UI.Settings.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.database.UserDatabase
import br.hthenrique.mygoalskotlin.Model.repository.RepositoryFirebase
import br.hthenrique.mygoalskotlin.Model.repository.RepositoryLocalDatabase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(val application: Application): ViewModel() {

    private var repositoryLocal: RepositoryLocalDatabase? = null
    private var repositoryFirebase: RepositoryFirebase? = null
    private var mutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null
    private var currentUid: String? = null
    private var currentUser: FirebaseUser? = null

    private var userDetailsLiveData: MutableLiveData<User>? = null

    init {
        repositoryFirebase = RepositoryFirebase()
        mutableLiveData = repositoryFirebase!!.getMutableLiveData()
        errorMutableLiveData = repositoryFirebase!!.getErrorMutableLiveData()

        this.currentUser = getCurrentUser()
        this.currentUid = recoverUid()

        val dao = UserDatabase.getDatabase(application).getUserDao()
        repositoryLocal = RepositoryLocalDatabase(dao)
    }

    fun deleteAccount(password: String?) {
        var currentUserDatabase: Array<User>? = null

        CoroutineScope(Dispatchers.IO).launch {
            currentUserDatabase = repositoryLocal?.getUserDatabase(currentUser?.uid)
        }

        if (currentUserDatabase!= null){
            val user: User = currentUserDatabase!![0]
            if (validateUser(password)){
                repositoryFirebase?.deleteAccountFirebase()
                deleteIntoDatabase(user)
            }
        }

    }

    private fun getCurrentUser(): FirebaseUser? {
        return repositoryFirebase!!.getCurrentUser()
    }

    fun getUserDetailsLiveData(): MutableLiveData<User>? {
        userDetailsLiveData = repositoryFirebase!!.getUserDetailsLiveData(currentUser?.uid)
        return userDetailsLiveData
    }

    private fun deleteIntoDatabase(user: User?){
        CoroutineScope(Dispatchers.IO).launch {
            repositoryLocal?.deleteUserDatabase(user)
        }
    }

    private fun validateUser(password: String?): Boolean {
        val recPassword = recoverPassword()
        if (password.equals(recPassword)){
            return true
        }
        return false
    }

    @SuppressLint("CommitPrefEdits")
    private fun recoverPassword(): String? {
        val password: String? = null
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        sharedPreferences.apply {
            getString("password", password)
        }
        return password
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
}