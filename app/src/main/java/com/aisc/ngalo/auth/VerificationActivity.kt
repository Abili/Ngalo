package com.aisc.ngalo.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aisc.ngalo.R
import com.aisc.ngalo.auth.ui.theme.NgaloTheme

class VerificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val phoneNumber = PhoneAuthHelper(this).getStoredPhoneNumber()
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    VerificationCodeScreen(phoneNumber = phoneNumber)
                }
            }
        }
    }
}

@Composable
fun VerificationCodeScreen(phoneNumber: String?) {
    var verificationCode by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ngalo_logo),
            contentDescription = "nalo_logo",
            modifier = Modifier.padding(bottom = 150.dp)
        )

        Text(
            "Enter Verification Code",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            "Verification code sent to $phoneNumber",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = { Text("Enter Verification Code") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                // Perform the action after the verification code is entered
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        Button(
            onClick = {
                if (verificationCode.isNotEmpty()) {
                    PhoneAuthHelper(context).verifyCode(verificationCode) { verified ->
                        if (verified) {
                            Toast.makeText(context, "Verified Successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify Code")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    NgaloTheme {
        VerificationCodeScreen("+256788243766")
    }
}