package com.aisc.ngalo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.auth.PhoneAuthHelper
import com.aisc.ngalo.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.hbb20.CountryCodePicker

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        val ref = FirebaseDatabase.getInstance()
        ref.setPersistenceEnabled(true)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser!=null){
            startActivity(Intent(this@SignUp, HomeActivity::class.java))
            finish()

        }


        // Set up click listener for phone authentication
        binding.phoneAuth.setOnClickListener {
            signIn("phone")
            binding.phoneauthLayout!!.visibility = View.VISIBLE
            binding.verifyCode!!.visibility = View.VISIBLE
            binding.phoneAuth.visibility = View.GONE
        }

        // Set up click listener for the "Sign Up" button
        binding.verifyCode!!.setOnClickListener {
            binding.phoneAuth.visibility = View.VISIBLE
            binding.verifyCode!!.visibility = View.GONE

            // Get the phone number from the form
            val countryCodePicker: CountryCodePicker = findViewById(R.id.ccp)
            val countryCode = countryCodePicker.selectedCountryCode
            val phoneNumber = binding.editTextPhone.text?.toString()

            if (!phoneNumber.isNullOrEmpty()) {
                binding.signInProgress.visibility = View.VISIBLE
                val phone = "+$countryCode$phoneNumber"

                // Send verification code
                PhoneAuthHelper(this).sendVerificationCode(phone) { success ->
                    if (success) {
                        binding.signInProgress.visibility = View.GONE
                        Snackbar.make(
                            binding.root, "Verification Successful", Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        if (binding.phoneAuth.visibility == View.GONE) {
                            binding.phoneAuth.visibility = View.VISIBLE
                        }
                        Snackbar.make(
                            binding.root, "Verification failed. Try Again", Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Snackbar.make(
                    binding.root, "PhoneNumber Required!", Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        // Set up click listener for sign-out
        binding.buttonSignOut.setOnClickListener {
            signOut()
        }

        // Set up click listener for Google sign-in
        binding.googleSignIn.setOnClickListener {
            signIn("google")
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

    private fun signIn(method: String) {
        // Store sign-in method in shared preferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("signInMethod", method)
        editor.apply()

        // Store sign-in state
        editor.putBoolean("isSignedIn", true)
        editor.apply()
    }

    private fun handleSignInResult(data: Intent?) {
        // Handle result of Google sign-in
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
        // Authenticate with Firebase using Google credentials
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                uploadImageToStorage(user!!.photoUrl!!)
                binding.signInProgress.visibility = View.GONE
            } else {
                Toast.makeText(
                    this, "Authentication failed.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val currentUser = auth.currentUser
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("profile_images/${FirebaseAuth.getInstance().currentUser!!.uid}")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Image uploaded successfully, get the download URL
                val downloadUrl = task.result
                // Pass the download URL to the updateUI method
                updateUI(currentUser, downloadUrl)
            } else {
                // Handle failure
                Snackbar.make(binding.root, "Failed To Create Profile !!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun updateUI(currentUser: FirebaseUser?, downloadUrl: Uri?) {
        if (currentUser != null) {
            binding.textViewStatus.text = getString(R.string.status_signed_in, currentUser.email)
            binding.phoneAuth.visibility = View.GONE
            binding.buttonSignOut.visibility = View.VISIBLE

            // Successfully signed in, navigate to UserProfile
            // ...
            val intent = Intent(this, UserProfile::class.java)
            intent.putExtra("signInMethod", getSignInMethod())
            intent.putExtra("imageUrl", downloadUrl.toString()) // Pass the download URL as a string
            intent.putExtra("email", currentUser.email)
            intent.putExtra("name", currentUser.displayName)
            startActivity(intent)
            finish()
            // ...

            Toast.makeText(
                this, "You are signed in as: ${currentUser.displayName}", Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            binding.textViewStatus.text = getString(R.string.status_signed_out)
            binding.phoneAuth.visibility = View.VISIBLE
            binding.buttonSignOut.visibility = View.GONE

            Toast.makeText(
                this, "Authentication failed.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(auth.currentUser, auth.currentUser!!.photoUrl)
    }

    private fun getSignInMethod(): String {
        // Retrieve sign-in method from shared preferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("signInMethod", "") ?: ""
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
