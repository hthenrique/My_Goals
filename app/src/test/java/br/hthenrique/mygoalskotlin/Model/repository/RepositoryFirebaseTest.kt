package br.hthenrique.mygoalskotlin.Model.repository

import androidx.lifecycle.MutableLiveData
import br.hthenrique.mygoalskotlin.Firebase.AuthListener
import br.hthenrique.mygoalskotlin.Model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.util.concurrent.Executor

internal class RepositoryFirebaseTest: AuthListener {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var repositoryFirebase: RepositoryFirebase

    private var logInResult = UNDEF

    private lateinit var successTask: Task<AuthResult>

    @Mock private var userMutableLiveData: MutableLiveData<FirebaseUser>? = null
    @Mock private var userDetailsLiveData: MutableLiveData<User>? = null
    @Mock private var errorMutableLiveData: MutableLiveData<String>? = null

    companion object{
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEF = 0
    }

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        repositoryFirebase = RepositoryFirebase()
        userMutableLiveData = MutableLiveData()
        userDetailsLiveData = MutableLiveData()
        errorMutableLiveData = MutableLiveData()
    }

    @Test
    fun registerNewUserTest(){
        doAnswer{
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(successTask)
            successTask
        }.`when`(successTask).addOnCompleteListener(ArgumentMatchers.any<OnCompleteListener<AuthResult>>())
        repositoryFirebase.registerNewUser(Mockito.any())
    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        logInResult = SUCCESS
    }

    override fun onFailure(message: String) {
        logInResult = FAILURE
    }
}