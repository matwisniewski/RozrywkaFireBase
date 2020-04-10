package com.example.rozrywkafirebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth



class MainFragment : Fragment() {
    companion object {
        const val TAG = "MainFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()

        buttonLogin.setOnClickListener{ launchSignInFlow() }
        buttonRegister.setOnClickListener{ launchSignInFlow() }
    }

    private fun logoutVisibility(){
        textView.isVisible = true
        buttonLogin.isVisible = true
        buttonRegister.isVisible = true
        textViewLogin.isVisible = false
        buttonBooks.isVisible = false
        buttonFilms.isVisible = false
        buttonGames.isVisible = false
        buttonLogout.isVisible = false
    }

    private fun loginVisibility(){
        textView.isVisible = false
        buttonLogin.isVisible = false
        buttonRegister.isVisible = false
        textViewLogin.isVisible = true
        buttonBooks.isVisible = true
        buttonFilms.isVisible = true
        buttonGames.isVisible = true
        buttonLogout.isVisible = true
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    textViewLogin.text = "Witaj ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    loginVisibility()
                    buttonLogout.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else -> {
                    logoutVisibility()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                loginVisibility()
                textViewLogin.text = "Witaj ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                logoutVisibility()
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch the sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE.
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }
}