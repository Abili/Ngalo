package com.aisc.ngalo.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.aisc.ngalo.UserProfile
import com.aisc.ngalo.VerifyPhoneNumber
import com.aisc.ngalo.models.Screens
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerifyPhone(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    fun sendVerificationCode(
        phoneNumber: String,
        verifPhone: Button,
        onComplete: (Boolean) -> Unit
    ) {
        if (phoneNumber == "") {
            Toast.makeText(context, "PhoneNumber Required", Toast.LENGTH_SHORT).show()
        } else {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(context as Activity)
                .setCallbacks(callbacks(onComplete, phoneNumber, verifPhone))
                .build()

            val smsRetrieverClient: SmsRetrieverClient = SmsRetriever.getClient(context)
            val task: Task<Void> = smsRetrieverClient.startSmsRetriever()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }

    private fun callbacks(onComplete: (Boolean) -> Unit, phoneNumber: String, verifPhone: Button) =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential, onComplete, verifPhone)
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
                this@VerifyPhone.verificationId = verificationId
                runOnUiThread {
                    if (context is VerificationCodeActivity) {
                        // Finish the current VerificationCodeActivity
                        (context as Activity).finish()
                    }
                    Toast.makeText(context, "Verification code sent", Toast.LENGTH_SHORT).show()
                    //savePhoneNumberToPrefs(phoneNumber)

                    // Launch VerificationActivity on successful code sent
                    val intent = Intent(context, VerifyPhoneNumber::class.java)
                    intent.putExtra("verificationId", verificationId)
                    intent.putExtra("phone", phoneNumber)
                    intent.putExtra("screen", Screens.phoneauth.name)
                    context.startActivity(intent)
                }
            }

        }

    fun getStoredPhoneNumber(): String? {
        val prefs = context.getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
        return prefs.getString("phoneNumber", null)
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onComplete: (Boolean) -> Unit,
        verifPhone: Button
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    runOnUiThread {
                        val intent = Intent(context, UserProfile::class.java)
                        intent.putExtra("signInMethod", "phone")
                        context.startActivity(intent)
                        onComplete(true)
                        verifPhone.visibility = View.GONE
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            context,
                            "Verification Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        onComplete(false)
                        verifPhone.visibility = View.VISIBLE
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
