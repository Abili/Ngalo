package com.aisc.ngalo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.auth.PhoneAuthHelper
import com.aisc.ngalo.databinding.ActivityVerificationCodeBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyPhoneNumber : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityVerificationCodeBinding
    lateinit var screen: String

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val phone = intent.getStringExtra("phone")
        val signInMethod = intent.getStringExtra("signInMethod")

        binding.buttonVerifyCode.setOnClickListener {
            binding.resendCode.visibility = View.VISIBLE
            binding.resendProgress.visibility = View.VISIBLE
            val verificationCode = binding.editTextVerificationCode.text.toString().trim()

            if (verificationCode.isNotEmpty()) {
                // If verification code is entered, manually verify
                val verificationId = intent.getStringExtra("verificationId")

                if (verificationId != null) {
                    val credential =
                        PhoneAuthProvider.getCredential(verificationId, verificationCode)
                    signInWithPhoneAuthCredential(credential, phone, signInMethod)
                } else {
                    showToast("Verification ID is null")
                }
            } else {
                binding.editTextVerificationCode.error = "Enter the verification code"
            }
        }
        binding.resendCode.setOnClickListener {

            PhoneAuthHelper(this).sendVerificationCode(phone!!) { success ->
                if (success) {
                    binding.resendCode.visibility = View.GONE
                    binding.resendProgress.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.root, "Verification Successful", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


        // Start SMS retrieval
        startSmsRetriever()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun startSmsRetriever() {
        val client: SmsRetrieverClient = SmsRetriever.getClient(this)

        // Starts SmsRetriever, waits for ONE matching SMS message until timeout
        val task: Task<Void> = client.startSmsRetriever()

        // Listen for success
        task.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Successfully started SMS retrieval
                // You can now listen for incoming SMS messages
                registerReceiver(
                    smsReceiver,
                    IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
                    RECEIVER_NOT_EXPORTED
                )
            } else {
                // Handle the error
                val exception = task.exception
                if (exception != null) {
                    // Handle the exception
                    showToast("Failed to start SMS retrieval: ${exception.message}")
                }
            }
        }
    }

    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
                val extras = intent.extras
                val smsMessage: SmsMessage? =
                    extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as SmsMessage?

                // Extract the verification code from the SMS
                val verificationCode = smsMessage?.messageBody?.substring(0, 6)

                // Automatically fill the verification code in the EditText
                binding.editTextVerificationCode.setText(verificationCode)

                // Stop SMS retrieval
                unregisterReceiver(this)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phone: String?,
        signInMethod: String?
    ) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, navigate to the next activity or perform desired action
                // For simplicity, we'll just display a success toast

                finish()
                showToast("Verification successful")


            } else {
                // Sign in failed, display a message to the user
                binding.resendCode.visibility = View.VISIBLE
                showToast("Authentication failed: ${task.exception?.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the SMS receiver when the activity is destroyed
        unregisterReceiver(smsReceiver)
    }
}
