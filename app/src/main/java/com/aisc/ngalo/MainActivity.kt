package com.aisc.ngalo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aisc.ngalo.models.BikeHireModel
import com.aisc.ngalo.models.items
import com.aisc.ngalo.navigation.NavGraph
import com.aisc.ngalo.navigation.Screens
import com.aisc.ngalo.ui.theme.NgaloTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                    //color = MaterialTheme.colors.background
                ) {
                    DefaultPreview()
                }
            }
        }
    }
}

@Composable
fun MainActivity(navController: NavController) {
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {

        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            val isLightTheme = MaterialTheme.colors.isLight
            Scaffold()
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(30.dp)
                .background(Color.Transparent)
        ) {


            Image(
                modifier = Modifier
                    .height(120.dp)
                    .padding(5.dp)
                    .width(120.dp)
                    .clickable(
                        onClick = {

                        }
                    ),
                painter = painterResource(id = R.drawable.bike_rides),
                contentDescription = null,

                )

            Image(
                modifier = Modifier
                    .height(120.dp)
                    .padding(5.dp)
                    .width(120.dp)
                    .clickable(
                        onClick = {

                        }
                    ),
                painter = painterResource(id = R.drawable.bike_repairs),
                contentDescription = null,

                )
        }
    }
}


@Composable
fun Scaffold() {

    val list = listOf(
        "name",
        "two",
        "three",
        "purple",
        "name",
        "two",
        "three",
        "purple",
        "name",
        "two",
        "three",
        "purple"
    )

    //val bikeViewModel: BikeViewModel by viewModel(factory = { BikeViewModel(repository) })

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ngalo",
                        color = Color.White
                    )
                },
                backgroundColor = colorResource(id = R.color.ngalo_green)
            )
            TopAppBar(
                title = {
                    Text(
                        "",
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Share, null)
                    }
                    IconButton(onClick = {/* Do Something*/ }) {
                        painterResource(R.drawable.filter_32)
                    }
                },
                modifier = Modifier.padding(top = 60.dp, end = 20.dp, start = 20.dp),
                backgroundColor = colorResource(id = R.color.white)
            )
        },
        content = { paddingValues ->
            Text(text = "names", Modifier.padding(paddingValues))
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items.forEach { item ->
                    item {
                        //Text("${com.aisc.ngalo.models.items}")
                        Card(
                            modifier = Modifier.padding(10.dp),
                            elevation = 10.dp
                        ) {
                            Image(
                                //image should come from Hirebikeitem
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentScale = ContentScale.Fit,
                                painter = painterResource(item.imageUrl),
                                contentDescription = null
                            )
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(top = 280.dp, bottom = 10.dp, end = 10.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.End
                            ) {


                                Text(
                                    text = item.itemName,
                                    modifier = Modifier
                                        .background(colorResource(id = R.color.ngalo_green)),
                                    color = colorResource(id = R.color.black),
                                    fontSize = 20.sp

                                )
                                Text(
                                    text = item.itemPrice,
                                    modifier = Modifier
                                        .padding(top = 5.dp),
                                    textAlign = TextAlign.End,
                                    fontSize = 20.sp
                                )


                            }
                        }
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NgaloTheme {
        val context = LocalContext.current
        //FirebaseApp.initializeApp(context)
        val navController = rememberNavController()
        NavGraph(navController = navController)
        MainActivity(navController)
        //  bikeViewModel.fetchData()

    }
}


