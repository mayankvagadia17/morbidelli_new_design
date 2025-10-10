package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FacebookLoginActivity : AppCompatActivity() {

    private lateinit var callbackManager: CallbackManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginButton: LoginButton
    private lateinit var btnSignOut: Button

    companion object {
        private const val TAG = "FacebookLoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)

        setupViews()
        setupFacebookLogin()
        setupClickListeners()
    }

    private fun setupViews() {
        loginButton = findViewById(R.id.login_button)
        btnSignOut = findViewById(R.id.btn_sign_out)
    }

    private fun setupFacebookLogin() {
        // Initialize Facebook SDK
        callbackManager = CallbackManager.Factory.create()
        firebaseAuth = FirebaseAuth.getInstance()

        // Set permissions for Facebook login
        loginButton.setPermissions("email", "public_profile")

        // Register callback for Facebook login
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "Facebook login successful")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "Facebook login cancelled")
                Toast.makeText(this@FacebookLoginActivity, "Facebook login cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Log.e(TAG, "Facebook login error", exception)
                Toast.makeText(this@FacebookLoginActivity, "Facebook login failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupClickListeners() {
        btnSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in
            Toast.makeText(this, "Welcome ${user.displayName}!", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "User signed in: ${user.displayName}")
            
            // You can navigate to the main activity or perform other actions here
            // For example:
            // startActivity(Intent(this, MainActivity::class.java))
            // finish()
        } else {
            // User is signed out
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "User signed out")
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
        updateUI(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }
}
