package br.hthenrique.mygoalskotlin.UI.Login.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.hthenrique.mygoalskotlin.Model.LoginModel
import br.hthenrique.mygoalskotlin.UI.Login.ViewModel.LoginViewModel
import br.hthenrique.mygoalskotlin.UI.Main.View.MainActivity
import br.hthenrique.mygoalskotlin.UI.Register.View.RegisterActivity
import br.hthenrique.mygoalskotlin.UI.ViewModelFactory.ViewModelFactory
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.INVALID_PARAMETERS
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.NETWORK_ERROR
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.NON_EXISTENT_USER
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.PASSWORD_INVALID_OR_DOES_EXIST
import br.hthenrique.mygoalskotlin.Utils.Validator
import br.hthenrique.mygoalskotlin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginModel: LoginModel by lazy { LoginModel() }
    private val validator: Validator by lazy { Validator() }
    private var loginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this,
            ViewModelFactory(this.application)).get(LoginViewModel::class.java)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonClicked()
    }

    private fun setupButtonClicked() {
        binding.buttonLogin.setOnClickListener {

            loginModel.email = binding.editTextEmail.text.toString().trim()
            loginModel.password = binding.editTextPassword.text.toString().trim()

            if (validateUserToLogin()){
                binding.loginProgressBar.visibility = View.VISIBLE
                binding.buttonLogin.isEnabled = false
                binding.buttonRegister.isEnabled = false
                firebaseRequest()
            }else{
                Toast.makeText(this, MessagesConstants.INVALID_PARAMETERS, Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            registerUser()
        }

        binding.textViewForgotPass.setOnClickListener {
            if (binding.editTextEmail.text.toString().trim() == ""){
                binding.editTextEmail.error = "Put a valid e-mail"
            }else{
                loginViewModel?.resetPassword(binding.editTextEmail.text.toString().trim())
                loginViewModel?.getErrorLiveData()?.observe(this, Observer {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                })
                Toast.makeText(this, "Verify your email", Toast.LENGTH_LONG).show()
            }
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
                binding.buttonRegister.isEnabled = true

                when(it.toString()){
                    NON_EXISTENT_USER -> {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                        registerUser()
                    }
                    PASSWORD_INVALID_OR_DOES_EXIST -> {
                        Toast.makeText(this, INVALID_PARAMETERS, Toast.LENGTH_LONG).show()
                    }
                    NETWORK_ERROR -> Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
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