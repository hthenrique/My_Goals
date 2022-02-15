package com.example.mygoalskotlin.Splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mygoalskotlin.Login.View.LoginActivity
import com.example.mygoalskotlin.Main.MainActivity
import com.example.mygoalskotlin.R
import com.example.mygoalskotlin.model.UserDTO
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private var userDTO: UserDTO = UserDTO()
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        sharedPreferences.getBoolean("isUserLogin", false)

        Handler().postDelayed({
            if (!sharedPreferences.contains("isUserLogin")) {
                startLoginActivity()
            } else {
                startMainActivity()
            }
        }, 3000)
    }

    private fun startLoginActivity() {
        val startLogin = Intent(this, LoginActivity::class.java)
        startActivity(startLogin)
        finish()
    }

    private fun startMainActivity() {
        val startMain: Intent = Intent(this, MainActivity::class.java)
        startActivity(startMain)
        finish()
    }
}
