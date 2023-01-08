package br.hthenrique.mygoalskotlin.UI.Profile.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.hthenrique.mygoalskotlin.Model.User
import br.hthenrique.mygoalskotlin.R
import br.hthenrique.mygoalskotlin.UI.Main.View.MainActivity
import br.hthenrique.mygoalskotlin.UI.Profile.ViewModel.ProfileViewModel
import br.hthenrique.mygoalskotlin.UI.ViewModelFactory.ViewModelFactory

import br.hthenrique.mygoalskotlin.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private val user: User by lazy { User() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.apply {
            title = "Profile"
            setDisplayHomeAsUpEnabled(true)
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this.application)
        profileViewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        loadUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.register_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                this.finish()
            }
            R.id.action_menu_done ->{
                saveUser()
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUser(){
        profileViewModel.getUserDetailsLiveData()?.observe(this, Observer {
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
        })
    }

    private fun showUserInfo(user: User) {
        binding.editTextNameUser.setText(user.name)
    }

    private fun saveUser(){
        user.name = binding.editTextNameUser.text.toString()
        profileViewModel.updateProfile(user)
    }
}