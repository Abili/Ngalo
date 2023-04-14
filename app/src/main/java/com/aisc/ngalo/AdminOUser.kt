package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aisc.ngalo.admin.AdminPanel
import com.aisc.ngalo.ui.theme.NgaloTheme
import com.google.firebase.auth.FirebaseAuth

class AdminOUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val curUID = FirebaseAuth.getInstance().currentUser!!.uid
                    val UID = FirebaseAuth.getInstance().uid
                    if (curUID == "LfgCFX1tqvMkOPOJOEzeDf4Pfwf2") {
                        AdminOrUser()
                    } else {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }


                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminOrUser() {
    val context = LocalContext.current
    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            onClick = {
                context.startActivity(Intent(context, AdminPanel::class.java))
            },
            Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(end = 10.dp),
            backgroundColor = colorResource(id = R.color.ngalo_green),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Admin",
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = 20.sp,
                    color = Color.White

                )

            }
        }

        Card(
            onClick = {
                context.startActivity(Intent(context, SignUp::class.java))
            },
            Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(start = 10.dp),
            backgroundColor = colorResource(id = R.color.ngalo_blue)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Users",
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    NgaloTheme {
        AdminOrUser()
    }
}