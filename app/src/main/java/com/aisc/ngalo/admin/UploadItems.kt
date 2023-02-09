package com.aisc.ngalo.admin

import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.startActivity
import com.aisc.ngalo.R
import com.aisc.ngalo.admin.ui.theme.NgaloTheme

class UploadItems : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                startActivity(context, Intent(context, CreateAdvert::class.java), null)
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(
                    getColor(
                        context,
                        R.color.ngalo_green
                    )
                )
            )
        ) {
            Text(
                text = "Create Advert", color = Color(getColor(context, R.color.ngalo_blue)),
                fontSize = 26.sp
            )
        }
        Button(
            onClick = {
                startActivity(context, Intent(context, AddBicyclePart::class.java), null)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(
                    getColor(
                        context,
                        R.color.ngalo_green
                    )
                )
            )
        ) {
            Text(
                text = "Add Bicycle part",
                color = Color(getColor(context, R.color.ngalo_blue)),
                fontSize = 26.sp
            )
        }
        Button(
            onClick = {
                startActivity(context, Intent(context, UploadBikeImages::class.java), null)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(
                    getColor(
                        context,
                        R.color.ngalo_green
                    )
                )
            )
        ) {
            Text(
                text = "Add Bicycle",
                color = Color(getColor(context, R.color.ngalo_blue)),
                fontSize = 26.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    NgaloTheme {
        Greeting2("Android")
    }
}