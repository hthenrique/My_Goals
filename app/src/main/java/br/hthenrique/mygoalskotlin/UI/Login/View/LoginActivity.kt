package br.hthenrique.mygoalskotlin.UI.Login.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.hthenrique.mygoalskotlin.UI.Login.ViewModel.LoginViewModel
import br.hthenrique.mygoalskotlin.UI.Login.ViewModel.LoginViewModelFactory
import br.hthenrique.mygoalskotlin.UI.Main.View.MainActivity
import br.hthenrique.mygoalskotlin.UI.Register.View.RegisterActivity
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants
import br.hthenrique.mygoalskotlin.Utils.Validator
import br.hthenrique.mygoalskotlin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var mockEmail = "henrique@gmail.com"
    private var mockpassword = "Henrique#3"

    private val loginModel: br.hthenrique.mygoalskotlin.Model.LoginModel by lazy { br.hthenrique.mygoalskotlin.Model.LoginModel() }
    private val validator: Validator by lazy { Validator() }
    private var loginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this,
            LoginViewModelFactory(this.application)).get(LoginViewModel::class.java)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextEmail.setText(mockEmail)
        binding.editTextPassword.setText(mockpassword)

        setupButtonClicked()
    }

    private fun setupButtonClicked() {
        binding.buttonLogin.setOnClickListener {

            loginModel.email = binding.editTextEmail.text.toString().trim()
            loginModel.password = binding.editTextPassword.text.toString().trim()

            if (validateUserToLogin()){
                binding.loginProgressBar.visibility = View.VISIBLE
                binding.buttonLogin.isEnabled = false
                firebaseRequest()
            }else{
                Toast.makeText(this, MessagesConstants.INVALID_PARAMETERS, Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun firebaseRequest(){

        loginViewModel?.login(loginModel)
        loginViewModel?.getLoginMutableLiveData()?.observe(this, Observer {
            if (it != null){
                loginUser()
            }
        })
        loginViewModel?.getErrorLiveData()?.observe(this, Observer {
            if (it != null){
                binding.loginProgressBar.visibility = View.GONE
                binding.buttonLogin.isEnabled = true
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                if(it.toString() != MessagesConstants.NETWORK_ERROR){
                    registerUser()
                }
            }
        })

    }

    private fun loginUser() {
        val loginIntent: Intent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun registerUser(){
        val registerIntent: Intent = Intent(this, RegisterActivity::class.java)
        registerIntent.putExtra("email", loginModel.email)
        startActivity(registerIntent)
    }

    private fun validateUserToLogin(): Boolean{
        val validator: Validator = Validator()
        var isValid: Boolean = false
        var isValidEmail: Boolean = false
        var isValidPassword: Boolean = false

        if (validator.isValidEmail(loginModel.email)){
            isValidEmail = true
        }

        if (validator.isValidPassword(loginModel.password)){
            isValidPassword = true
        }

        if (isValidEmail && isValidPassword){
            isValid = true
        }

        return isValid
    }
}