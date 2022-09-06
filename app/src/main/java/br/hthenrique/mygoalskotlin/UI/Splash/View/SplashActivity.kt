package br.hthenrique.mygoalskotlin.UI.Splash.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import br.hthenrique.mygoalskotlin.UI.Login.View.LoginActivity
import br.hthenrique.mygoalskotlin.UI.Main.View.MainActivity
import br.hthenrique.mygoalskotlin.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.i("Splash Screen", "Starting splash screen")

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSaved", Context.MODE_PRIVATE)
        sharedPreferences.getBoolean("isUserLogin", false)

        Handler().postDelayed({
            if (!sharedPreferences.contains("isUserLogin")) {
                Log.i("Splash Screen", "Starting login screen")
                startLoginActivity()
            } else {
                Log.i("Splash Screen", "Starting main screen")
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
        val startMain = Intent(this, MainActivity::class.java)
        startActivity(startMain)
        finish()
    }
}
