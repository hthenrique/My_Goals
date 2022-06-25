package com.example.mygoalskotlin.UI.Login.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mygoalskotlin.Model.LoginModel
import com.example.mygoalskotlin.UI.Login.ViewModel.LoginViewModel
import com.example.mygoalskotlin.UI.Main.View.MainActivity
import com.example.mygoalskotlin.UI.Register.View.RegisterActivity
import com.example.mygoalskotlin.Utils.MessagesConstants
import com.example.mygoalskotlin.Utils.Validator
import com.example.mygoalskotlin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var mockEmail = "henrique@gmail.com"
    private var mockpassword = "Henrique#3"

    private val loginModel: LoginModel by lazy { LoginModel() }
    private val validator: Validator by lazy { Validator() }
    private var loginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

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
                saveUserInSharedPrefs()
            }
        })
        loginViewModel?.getErrorLiveData()?.observe(this, Observer {
            if (it != null){
                binding.loginProgressBar.visibility = View.GONE
                binding.buttonLogin.isEnabled = true
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                registerUser()
            }
        })

    }

    @SuppressLint("CommitPrefEdits")
    private fun saveUserInSharedPrefs() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        prefsEditor.putBoolean("isUserLogin", true)
        prefsEditor.putString("email", loginModel.email)
        prefsEditor.putString("password", loginModel.password)
        prefsEditor.apply()
        prefsEditor.commit()

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