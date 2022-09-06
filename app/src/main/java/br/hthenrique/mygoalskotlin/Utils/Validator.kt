package br.hthenrique.mygoalskotlin.Utils

import java.util.regex.Matcher
import java.util.regex.Pattern

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Validator {

    fun isValidEmail(email: String?): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        val passwordLength: Int = 8

        return if (password?.length!! >= passwordLength){
            val pattern: Pattern
            val passwordPatter = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            pattern = Pattern.compile(passwordPatter)
            val matcher: Matcher = pattern.matcher(password)
            matcher.matches()
        }else{
            false
        }
    }
}