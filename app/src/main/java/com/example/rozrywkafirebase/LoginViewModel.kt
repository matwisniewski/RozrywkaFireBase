package com.example.rozrywkafirebase

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel : ViewModel() {
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
}