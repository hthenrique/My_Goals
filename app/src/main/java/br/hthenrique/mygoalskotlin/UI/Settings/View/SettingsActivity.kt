package br.hthenrique.mygoalskotlin.UI.Settings.View

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.hthenrique.mygoalskotlin.R
import br.hthenrique.mygoalskotlin.UI.Login.View.LoginActivity
import br.hthenrique.mygoalskotlin.UI.Settings.ViewModel.SettingsViewModel
import br.hthenrique.mygoalskotlin.UI.ViewModelFactory.ViewModelFactory
import br.hthenrique.mygoalskotlin.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this.application)
        settingsViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        setSettingsItems()
        itemsSelected()
    }

    private fun setSettingsItems() {
        val itemList = ArrayList<String>()
        itemList.add("Delete account")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        binding.listSettings.adapter = arrayAdapter
    }

    private fun itemsSelected() {
        binding.listSettings.apply {
            setOnItemClickListener { adapterView, view, position, id ->
                val selectedItem = this.getItemAtPosition(position)
                actionItemSelected(selectedItem.toString())
            }

        }
    }

    private fun actionItemSelected(selectedItem: String) {
        when(selectedItem){
            "Delete account" -> {
                deleteAccount()
            }
        }
    }

    private fun deleteAccount() {
        deleteConfirmation()
    }

    private fun deleteConfirmation(){
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("Delete account")
            .setMessage("Are you sure you want to delete your account?\n" +
                    "This process is irreversible.")
            .setPositiveButton("Yes") { dialog, id ->
                validatePassword()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun validatePassword(){
        val builder = AlertDialog.Builder(this)
        val layoutParameter = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParameter.setMargins(6, 0, 6, 0)

        val inputPassword = EditText(this)
        inputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        inputPassword.layoutParams = layoutParameter

        builder
            .setTitle("Confirm your password")
            .setView(inputPassword)
            .setPositiveButton("Confirm") { dialog, id ->
                val password = inputPassword.text.toString()
                val deletedUser = settingsViewModel.deleteAccount(password)
                if(deletedUser.equals("User Deleted")){
                    val backToLogin = Intent(this, LoginActivity::class.java)
                    backToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(backToLogin)
                    finishAffinity()
                }
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
        // Create the AlertDialog object and return it
        builder.create().show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}