package com.example.mygoalskotlin.Model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

class FirebaseResponse {

    var userMutableLiveData: MutableLiveData<FirebaseUser>? = null

    var errorMutableLiveData: MutableLiveData<String>? = null

}