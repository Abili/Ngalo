package com.aisc.ngalo.models

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aisc.ngalo.R
import com.aisc.ngalo.ui.HireBikeItem
import com.aisc.ngalo.ui.ui.theme.NgaloTheme

class BikeHireModel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HirePreview()
                }
            }
        }
    }
}

@Composable
fun BikeHireModel(navHostController: NavHostController) {
    Column {
        TopAppBar(
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Localized description")
                }
            }
        )
        Image(
            //image should come from Hirebikeitem
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_ph),
            contentDescription = null
        )
        Box(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.BottomEnd
        ) {


            Text(
                text = "Draagon black tiger",
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .background(colorResource(id = R.color.ngalo_green)),
                color = colorResource(id = R.color.black),
                fontSize = 20.sp

            )
            Text(
                text = "ugx 240,000",
                modifier = Modifier
                    .padding(top = 10.dp),
                textAlign = TextAlign.End,
                fontSize = 20.sp
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun HirePreview() {
    val navController = rememberNavController()
    NgaloTheme {
        BikeHireModel(navController)
    }
}