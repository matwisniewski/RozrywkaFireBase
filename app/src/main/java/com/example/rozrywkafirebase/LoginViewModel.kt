package com.example.rozrywkafirebase

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import kotlin.random.Random

class LoginViewModel : ViewModel() {

    companion object {
        val androidFacts = arrayOf(
            "u nas możesz stworzyć własną bibliotekę filmów",
            "u nas możesz stworzyć własną bibliotekę gier",
            "U nas możesz stworzyć własną bibliotekę książek"
        )
    }
        enum class AuthenticationState {
            AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
        }

        val authenticationState = FirebaseUserLiveData().map { user ->
            if (user != null) {
                AuthenticationState.AUTHENTICATED
            } else {
                AuthenticationState.UNAUTHENTICATED
            }
        }

    fun getFactToDisplay(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val factTypePreferenceKey = context.getString(R.string.preference_fact_type_key)
        val funFactType = sharedPreferences.getString(factTypePreferenceKey, null)

        return androidFacts[Random.nextInt(0, androidFacts.size)]
    }

}