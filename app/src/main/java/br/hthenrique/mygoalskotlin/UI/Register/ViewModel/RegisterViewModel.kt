package br.hthenrique.mygoalskotlin.UI.Register.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.hthenrique.mygoalskotlin.Model.RegisterModel
import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.Model.repository.RepositoryFirebase
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel : ViewModel() {

    private var repositoryFirebase: RepositoryFirebase? = null
    private var mutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var errorMutableLiveData: MutableLiveData<String>? = null

    init {
        repositoryFirebase = RepositoryFirebase()
        mutableLiveData = repositoryFirebase!!.getMutableLiveData()
        errorMutableLiveData = repositoryFirebase!!.getErrorMutableLiveData()
    }

    fun saveUserDetails() {
        var user: User = User()
        user.uid = mutableLiveData?.value?.uid!!
        user.email = mutableLiveData?.value?.email
        repositoryFirebase!!.saveUserDetailsFirebase(user)
    }


    fun signup(registerModel: RegisterModel){
        repositoryFirebase?.registerNewUser(registerModel)
    }

    fun getMutableLiveData(): MutableLiveData<FirebaseUser>? {
        return mutableLiveData
    }

    fun getErrorLiveData(): MutableLiveData<String>?{
        return errorMutableLiveData
    }
}