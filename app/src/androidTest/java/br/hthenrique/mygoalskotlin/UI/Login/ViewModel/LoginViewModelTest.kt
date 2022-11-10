package br.hthenrique.mygoalskotlin.UI.Login.ViewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.hthenrique.mygoalskotlin.Model.LoginModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class LoginViewModelTest{

    @get:Rule
    private val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var application: Application
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginModel: LoginModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        application = Mockito.mock(Application::class.java)
        loginViewModel = LoginViewModel(application)
        loginModel = LoginModel()
        loginModel.email = "test@test.com"
        loginModel.password = "test#123"
    }

    @Test
    fun loginTestSuccess(){
        loginViewModel.login(loginModel)
    }

    @Test
    fun getLoginMutableLiveDataTestSuccess(){
        loginViewModel.login(getLoginModelMock())
        Assert.assertNotNull(loginViewModel.getLoginMutableLiveData())
    }

    fun getLoginModelMock(): LoginModel{
        val loginModel = LoginModel()
        loginModel.email = "test@test.com"
        loginModel.password = "test#123"

        return loginModel
    }

}