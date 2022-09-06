package br.hthenrique.mygoalskotlin.UI.Main.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.R
import br.hthenrique.mygoalskotlin.UI.Login.View.LoginActivity
import br.hthenrique.mygoalskotlin.UI.Main.ViewModel.MainViewModel
import br.hthenrique.mygoalskotlin.UI.Main.ViewModel.MainViewModelFactory
import br.hthenrique.mygoalskotlin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val user: User by lazy { User() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = MainViewModelFactory(this.application)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.loadingLayout.visibility = View.VISIBLE
        mainViewModel.getUserDetailsLiveData()?.observe(this, Observer {
            binding.loadingLayout.visibility = View.GONE
            user.apply {
                user.uid = it.uid
                name = it.name
                email = it.email
                position = it.position
                goals = it.goals
                matches = it.matches
                lastUpdate = it.lastUpdate
            }
            showUserInfo(user)
            setupButtonClick()
        })
    }

    private fun setupButtonClick() {
        binding.editPositionButton.setOnClickListener { showOrHideEditPosition() }
        binding.editDoneButton.setOnClickListener { savePositionValue() }
        binding.cancelEditButton.setOnClickListener { showOrHideEditPosition() }

        //Goals
        binding.decreaseGoalButton.setOnClickListener {
            user.goals = decrease(user.goals)
            binding.goalsValue.text = user.goals.toString()
            saveDetails()
        }
        binding.addGoalButton.setOnClickListener {
            user.goals = increment(user.goals)
            binding.goalsValue.text = user.goals.toString()
            saveDetails()
        }

        //Matches
        binding.decreaseMatchButton.setOnClickListener {
            user.matches = decrease(user.matches)
            binding.matchesValue.text = user.matches.toString()
            saveDetails()
        }
        binding.addMatchButton.setOnClickListener {
            user.matches = increment(user.matches)
            binding.matchesValue.text = user.matches.toString()
            saveDetails()
        }
    }

    private fun saveDetails(){
        mainViewModel.saveDetails(user)
    }

    private fun increment(variable: Int?): Int {
        return variable!! + 1
    }

    private fun decrease(variable: Int?): Int {
        while (variable!! > 0){
            return variable - 1
        }
        return variable
    }

    private fun showOrHideEditPosition() {
        if (binding.editPositionNameLayout.visibility == View.VISIBLE){
            binding.editPositionNameLayout.visibility = View.GONE
        }else{
            binding.editPositionNameLayout.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logoutUser -> {
                logoutUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun logoutUser() {
        Firebase.auth.signOut()
        deleteUserFromSharedPrefs()
        mainViewModel.deleteUser(user)
        backToLogin()
    }

    @SuppressLint("SetTextI18n")
    private fun showUserInfo(user: User) {
        if (user.name.equals(null) || user.name.equals("null")){
            binding.welcomeUser.text = "Welcome! "
        }

        binding.welcomeUser.text = "Welcome! ${user.name}"
        binding.userPositionValue.text = user.position
        binding.goalsValue.text = user.goals.toString()
        binding.matchesValue.text = user.matches.toString()

    }

    private fun deleteUserFromSharedPrefs() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        prefsEditor.remove("isUserLogin")
        prefsEditor.apply()
        prefsEditor.commit()
    }

    private fun backToLogin() {
        val startLogin: Intent = Intent(this, LoginActivity::class.java)
        startActivity(startLogin)
        finish()
    }

    private fun savePositionValue() {
        user.position = binding.editTextPosition.text.toString()
        binding.userPositionValue.text = user.position
        saveDetails()
        showOrHideEditPosition()
    }
}