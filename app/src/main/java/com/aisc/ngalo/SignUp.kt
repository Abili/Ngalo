package com.aisc.ngalo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.aisc.ngalo.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    // ViewBinding variable for the login form layout
    private lateinit var binding: ActivitySignUpBinding

    // FirebaseAuth instance
    private lateinit var auth: FirebaseAuth

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                handleSignInResult(data)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themeOption = sharedPreferences.getString("theme", "0")
        ThemeHelper.applyTheme(themeOption!!)


        val ref = FirebaseDatabase.getInstance().getReference("bikes")
        ref.keepSynced(true)
        FirebaseApp.initializeApp(this)
        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            // User is already signed in, skip the sign-up screen
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Set up click listener for the "Sign Up" button
        binding.buttonSignUp.setOnClickListener {
            // Get the email and password from the form
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Create a new account with the email and password
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Account creation successful
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    startActivity(Intent(this, UserProfile::class.java))
                    finish()
                } else {
                    // Account creation failed
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        }

        // Set up click listener for the "Log In" button
        binding.buttonLogIn.setOnClickListener {
            // Get the email and password from the form
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Log in with the email and password
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Log in successful
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    startActivity(Intent(this, UserProfile::class.java))
                    finish()
                } else {
                    // Log in failed
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        }
        binding.buttonSignOut.setOnClickListener {
            signOut()
        }
        binding.googleSignIn.setOnClickListener {
            binding.signInProgress.visibility = View.VISIBLE
            signInWithGoogle(this)
        }
    }

    private fun signInWithGoogle(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent

        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            if (task.isSuccessful) {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } else {
                Toast.makeText(
                    this, "Google Sign In failed", Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                binding.signInProgress.visibility = View.GONE
            } else {
                Toast.makeText(
                    this, "Authentication failed.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Update the UI based on the current user
    @SuppressLint("StringFormatInvalid")
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            // User is signed in, show the main app screen
            binding.textViewStatus.text = getString(R.string.status_signed_in, currentUser.email)
            binding.buttonSignUp.visibility = View.GONE
            binding.buttonLogIn.visibility = View.GONE
            binding.buttonSignOut.visibility = View.VISIBLE

            // Successfully signed in, navigate to the next screen
            // navController.navigate(Screens.AdminOrUser.route)
            startActivity(Intent(this, HomeActivity::class.java))
            Toast.makeText(
                this, "You are signed in as: ${currentUser.displayName}", Toast.LENGTH_SHORT
            ).show()
            finish()

        } else {
            // User is signed out, show the login form
            binding.textViewStatus.text = getString(R.string.status_signed_out)
            binding.buttonSignUp.visibility = View.VISIBLE
            binding.buttonLogIn.visibility = View.VISIBLE
            binding.buttonSignOut.visibility = View.GONE

            Toast.makeText(
                this, "Authentication failed.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Sign out the current user
    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

