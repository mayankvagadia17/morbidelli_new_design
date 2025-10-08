package com.morbidelli.morbidelli_design

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthManager {
    private const val TAG = "AuthManager"
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun signOut() {
        firebaseAuth.signOut()
        Log.d(TAG, "User signed out")
    }

    fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun getUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun getUserDisplayName(): String? {
        return firebaseAuth.currentUser?.displayName
    }

    fun getUserPhotoUrl(): String? {
        return firebaseAuth.currentUser?.photoUrl?.toString()
    }
}
