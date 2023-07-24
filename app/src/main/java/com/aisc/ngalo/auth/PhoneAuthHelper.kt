package com.aisc.ngalo.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.aisc.ngalo.HomeActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAuthHelper(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    fun sendVerificationCode(phoneNumber: String, onComplete: (Boolean) -> Unit) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(callbacks(onComplete, phoneNumber))
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String, onComplete: (Boolean) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential, onComplete)
    }

    private fun callbacks(onComplete: (Boolean) -> Unit, phoneNumber: String) =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential, onComplete)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                runOnUiThread {
                    Toast.makeText(
                        context,
                        "Verification Failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    onComplete(false)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                this@PhoneAuthHelper.verificationId = verificationId
                runOnUiThread {
                    Toast.makeText(context, "Verification code sent", Toast.LENGTH_SHORT).show()
                    savePhoneNumberToPrefs(phoneNumber)
                }
            }
        }
    fun getStoredPhoneNumber(): String? {
        val prefs = context.getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
        return prefs.getString("phoneNumber", null)
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onComplete: (Boolean) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    runOnUiThread {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                        onComplete(true)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            context,
                            "Verification Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        onComplete(false)
                    }
                }
            }
    }

    private fun runOnUiThread(action: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(action)
    }

    private fun savePhoneNumberToPrefs(phoneNumber: String?) {
        phoneNumber?.let {
            val prefs = context.getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
            prefs.edit().putString("phoneNumber", it).apply()
        }
    }
}
