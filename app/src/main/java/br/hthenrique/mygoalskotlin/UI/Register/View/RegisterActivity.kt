package br.hthenrique.mygoalskotlin.UI.Register.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.hthenrique.mygoalskotlin.Utils.Validator
import br.hthenrique.mygoalskotlin.databinding.ActivityRegisterBinding
import br.hthenrique.mygoalskotlin.Model.RegisterModel
import br.hthenrique.mygoalskotlin.UI.Register.ViewModel.RegisterViewModel
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.INVALID_EMAIL
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.INVALID_PASSWORD
import br.hthenrique.mygoalskotlin.Utils.MessagesConstants.NO_MATCH_PASSWORD
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val validator: Validator by lazy { Validator() }
    private val registerModel: br.hthenrique.mygoalskotlin.Model.RegisterModel by lazy { br.hthenrique.mygoalskotlin.Model.RegisterModel() }

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

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
            registerModel.name = binding.editTextRegisterName.text.toString().trim()
            registerModel.email = binding.editTextRegisterEmail.text.toString().trim()
            registerModel.password = binding.editTextPassword.text.toString().trim()
            registerModel.confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            isValidUser(isValidUserToRegister())
        }
    }

    private fun isValidUser(valid: Boolean){
        if (valid){
            binding.registerProgressBar.visibility = View.VISIBLE
            registerViewModel.signup(registerModel)
            registerViewModel.getMutableLiveData()?.observe(this, Observer {
                if (it != null){
                    registerViewModel.saveUserDetails()
                    onBackPressed()
                    this.finish()
                }
            })
            registerViewModel.getErrorLiveData()?.observe(this, Observer {
                if (it != null){
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            })

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