package br.hthenrique.mygoalskotlin.UI.Settings.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.hthenrique.mygoalskotlin.Model.LoginModel
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
    private var loginModel: LoginModel? = null

    private var userDetailsLiveData: MutableLiveData<User>? = null

    init {
        repositoryFirebase = RepositoryFirebase()
        mutableLiveData = repositoryFirebase!!.getMutableLiveData()
        errorMutableLiveData = repositoryFirebase!!.getErrorMutableLiveData()

        this.currentUser = getCurrentUser()
        this.currentUid = recoverUid()

        getUserDetailsLiveData()

        val dao = UserDatabase.getDatabase(application).getUserDao()
        repositoryLocal = RepositoryLocalDatabase(dao)

        this.loginModel = recoverCredentials()
    }

    fun deleteAccount(password: String?):String? {
        var userDeleted: String? = null
        var currentUser = userDetailsLiveData?.value

        if (currentUser!= null){
            val user: User = currentUser
            if (validateUser(password)){
                deleteIntoDatabase(user)
                repositoryFirebase?.deleteAccountFirebase(loginModel)
                userDeleted = "User Deleted"
                deleteUserFromSharedPrefs()
            }
        }
        return userDeleted
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
        val recPassword = loginModel?.password
        if (password.equals(recPassword)){
            return true
        }
        return false
    }

    @SuppressLint("CommitPrefEdits")
    private fun recoverCredentials(): LoginModel? {
        var userLogin: LoginModel? = LoginModel()
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)

        userLogin?.apply {
            email = sharedPreferences.all["email"].toString()
            password = sharedPreferences.all["password"].toString()
        }
        return userLogin
    }

    fun deleteUserFromSharedPrefs() {
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        prefsEditor.remove("isUserLogin")
        prefsEditor.apply()
        prefsEditor.commit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun recoverUid(): String? {
        val uid: String? = String()
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        sharedPreferences.apply {
            getString("uid", uid)
        }
        return uid
    }
}