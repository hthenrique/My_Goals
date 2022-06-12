package com.example.mygoalskotlin.UI.Register.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mygoalskotlin.Utils.Validator
import com.example.mygoalskotlin.databinding.ActivityRegisterBinding
import com.example.mygoalskotlin.Model.RegisterModel
import com.example.mygoalskotlin.Utils.MessagesConstants.INVALID_EMAIL
import com.example.mygoalskotlin.Utils.MessagesConstants.INVALID_PASSWORD
import com.example.mygoalskotlin.Utils.MessagesConstants.NO_MATCH_PASSWORD
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val validator: Validator by lazy { Validator() }
    private val registerModel: RegisterModel by lazy { RegisterModel() }
    //private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(baseContext)
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = getIntent()
        if (intent != null){
            registerModel.email = intent.getStringExtra("email")
            binding.editTextRegisterEmail.setText(registerModel.email)
        }

        setupButtonRegister()
    }

    private fun setupButtonRegister() {

        binding.buttonCreateUser.setOnClickListener {
            registerModel.email = binding.editTextRegisterEmail.text.toString().trim()
            registerModel.password = binding.editTextPassword.text.toString().trim()
            registerModel.confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            isValidUser(isValidUserToRegister())
        }
    }

    private fun isValidUser(valid: Boolean){
        if (valid){
            firebaseAuth?.createUserWithEmailAndPassword(
                registerModel.email.toString(),
                registerModel.password.toString()
            )
                ?.addOnCompleteListener {
                    binding.registerProgressBar.visibility = View.VISIBLE
                    if (it.isSuccessful){
                        onBackPressed()
                        this.finish()
                    }else{
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun isValidUserToRegister(): Boolean{
        var isValidUser: Boolean = false
        var isValidEmail: Boolean = false
        var isValidPassword: Boolean = false
        var isPasswordsEquals: Boolean = false

        if (validator.isValidEmail(registerModel.email)){
            isValidEmail = true
        }else{
            Toast.makeText(this, INVALID_EMAIL, Toast.LENGTH_LONG).show()
        }

        if (validator.isValidPassword(registerModel.password)){
            isValidPassword = true
            binding.txtError.visibility = View.INVISIBLE
        }else{
            binding.txtError.text = INVALID_PASSWORD
            binding.txtError.visibility = View.VISIBLE
        }

        if (registerModel.confirmPassword.equals(registerModel.password)){
            isPasswordsEquals = true
        }else{
            Toast.makeText(this, NO_MATCH_PASSWORD, Toast.LENGTH_LONG).show()
        }

        if (isValidEmail && isValidPassword && isPasswordsEquals){
            isValidUser = true
        }

        return isValidUser
    }
}