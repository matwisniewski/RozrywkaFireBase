package com.example.rozrywkafirebase.data

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.rozrywkafirebase.R
import com.example.rozrywkafirebase.data.model.LoggedInUser
import com.example.rozrywkafirebase.ui.login.LoggedInUserView
import com.example.rozrywkafirebase.ui.login.LoginFormState
import com.example.rozrywkafirebase.ui.login.LoginResult
import java.io.IOException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    // in-memory cache of the loggedInUser object
    var user: FirebaseUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        auth.signOut();
    }

    fun register(email: String, password: String, state: MutableLiveData<LoginResult>)/* : Result<LoggedInUser>*/ {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    user = auth.currentUser
//                    updateUI(user)
                    state.value = LoginResult(success = LoggedInUserView(displayName = user!!.email!!))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(activity, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    state.value = LoginResult(error = R.string.register_failed)
                }
            }

//        return if (user != null) {
//            Result.Success(LoggedInUser(java.util.UUID.randomUUID().toString(), user!!.displayName!!))
//        } else {
//            Result.Error(IllegalArgumentException("Cannot register!"))
//        }
    }

    fun login(username: String, password: String, state: MutableLiveData<LoginResult>)/*: Result<LoggedInUser>*/ {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    user = auth.currentUser
//                    updateUI(user)
                    state.value = LoginResult(success = LoggedInUserView(displayName = user!!.email!!))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(activity, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    state.value = LoginResult(error = R.string.login_failed)
                }
            }
//
//        return if (user != null) {
//            Result.Success(LoggedInUser(java.util.UUID.randomUUID().toString(), user!!.displayName!!))
//        } else {
//            Result.Error(IllegalArgumentException("Cannot register!"))
//        }
    }
}
