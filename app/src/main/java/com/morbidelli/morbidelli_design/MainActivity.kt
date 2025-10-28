package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.morbidelli.morbidelli_design.adapter.RideAdapter
import com.morbidelli.morbidelli_design.model.RideModel

class MainActivity : AppCompatActivity() {

    private lateinit var cardUpcoming: CardView
    private lateinit var cardCompleted: CardView
    private lateinit var cardCancelled: CardView
    private lateinit var btnBookTestRide: Button
    private lateinit var btnToggle: Button
    private lateinit var btn_profile: Button
    private lateinit var btnGoogleSignIn: SignInButton
    private lateinit var btnFacebookLogin: Button
    private lateinit var btnMotorcycleModels: Button
    private lateinit var btnNews: Button
    private lateinit var tvUpcomingCount: TextView
    private lateinit var tvCompletedCount: TextView
    private lateinit var tvCancelledCount: TextView

    private lateinit var llBookingCards: LinearLayout
    private lateinit var llEmptyState: ScrollView
    private lateinit var rvRides: RecyclerView
    private lateinit var rideAdapter: RideAdapter

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private var isShowingEmptyState = false

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupGoogleSignIn()
        setupClickListeners()
        setupRidesRecyclerView()
        updateCounts()
    }

    private fun setupViews() {
        cardUpcoming = findViewById(R.id.card_upcoming)
        cardCompleted = findViewById(R.id.card_completed)
        cardCancelled = findViewById(R.id.card_cancelled)
        btnBookTestRide = findViewById(R.id.btn_book_test_ride)
        btnToggle = findViewById(R.id.btn_toggle)
        btn_profile = findViewById(R.id.btn_profile)
        btnGoogleSignIn = findViewById(R.id.btn_google_sign_in)
        btnFacebookLogin = findViewById(R.id.btn_facebook_login)
        btnMotorcycleModels = findViewById(R.id.btn_motorcycle_models)
        btnNews = findViewById(R.id.btn_news)
        tvUpcomingCount = findViewById(R.id.tv_upcoming_count)
        tvCompletedCount = findViewById(R.id.tv_completed_count)
        tvCancelledCount = findViewById(R.id.tv_cancelled_count)

        llBookingCards = findViewById(R.id.ll_booking)
        llEmptyState = findViewById(R.id.ll_empty_state)
        rvRides = findViewById(R.id.rv_rides)
    }

    private fun setupGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun setupClickListeners() {
        cardUpcoming.setOnClickListener {
            startActivity(Intent(this, TestRideActivity::class.java))
        }

        cardCompleted.setOnClickListener {
            startActivity(Intent(this, MyBookingActivity::class.java))
        }

        cardCancelled.setOnClickListener {
            startActivity(Intent(this, ModelSliderActivity::class.java))
        }

        btnBookTestRide.setOnClickListener {
            startActivity(Intent(this, ContactDetailsActivity::class.java))
        }

        btn_profile.setOnClickListener {
           startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        btnFacebookLogin.setOnClickListener {
            startActivity(Intent(this, FacebookLoginActivity::class.java))
        }

        btnMotorcycleModels.setOnClickListener {
            startActivity(Intent(this, MotorcycleModelsActivity::class.java))
        }

        btnNews.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }
        
        // Add test ride feedback button
        findViewById<Button>(R.id.btn_test_feedback)?.setOnClickListener {
            startActivity(Intent(this, TestRideFeedbackActivity::class.java))
        }

        btnToggle.setOnClickListener {
            toggleEmptyState()
        }
    }

    private fun setupRidesRecyclerView() {
        val rideList = listOf(
            RideModel(
                id = 1,
                bikeName = getString(R.string.n300_model),
                bikeImage = R.drawable.ic_bike_n300,
                powerSpec = "Power: 295/10000 HP",
                weightSpec = "Curb Weight: 161 KG"
            ),
            RideModel(
                id = 2,
                bikeName = getString(R.string.n250r_model),
                bikeImage = R.drawable.ic_bike_n250r,
                powerSpec = "Power: 25.75/9250 HP",
                weightSpec = "Curb Weight: 151 KG"
            ),
            RideModel(
                id = 3,
                bikeName = "N400 Sport",
                bikeImage = R.drawable.ic_bike_n300,
                powerSpec = "Power: 400/11000 HP",
                weightSpec = "Curb Weight: 175 KG"
            )
        )

        rideAdapter = RideAdapter(rideList) { selectedRide ->
            onRideSelected(selectedRide)
        }

        rvRides.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = rideAdapter
            setHasFixedSize(true)
        }
    }

    private fun onRideSelected(ride: RideModel) {
        startActivity(
            Intent(this, BookTestRideActivity::class.java)
        )
    }

    private fun updateCounts() {
        tvUpcomingCount.text = "4"
        tvCompletedCount.text = "18"
        tvCancelledCount.text = "7"
    }

    private fun toggleEmptyState() {
        if (isShowingEmptyState) {
            llBookingCards.visibility = View.VISIBLE
            llEmptyState.visibility = View.GONE
            isShowingEmptyState = false
        } else {
            llBookingCards.visibility = View.GONE
            llEmptyState.visibility = View.VISIBLE
            isShowingEmptyState = true
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
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
        } else {
            // User is signed out
            Log.d(TAG, "User signed out")
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }
}
