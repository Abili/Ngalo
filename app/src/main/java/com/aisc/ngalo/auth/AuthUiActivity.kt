package com.aisc.ngalo.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aisc.ngalo.AdminOUserActivity
import com.aisc.ngalo.R
import com.aisc.ngalo.ui.ui.theme.NgaloTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthUiActivity : ComponentActivity() {
    private lateinit var navController: NavController
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
        setContent {
            NgaloTheme {
                navController = rememberNavController()
                auth = Firebase.auth

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignInScreen(navController)
                }
            }
        }
    }

    private fun signInWithGoogle(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
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
                    this,
                    "Google Sign In failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Successfully signed in, navigate to the next screen
            // navController.navigate(Screens.AdminOrUser.route)
            startActivity(Intent(this, AdminOUserActivity::class.java))

            Toast.makeText(
                this,
                "You are signed in as: ${user.displayName}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Composable
    fun SignInScreen(navController: NavController) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ngalo_logo),
                contentDescription = "Ngalo_logo"
            )
            GoogleSignInButton(onClick = { signInWithGoogle(context) })
        }
    }

    @Composable
    fun GoogleSignInButton(onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text("Sign in with Google")
        }
    }

}
