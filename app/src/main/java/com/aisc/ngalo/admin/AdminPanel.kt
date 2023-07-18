package com.aisc.ngalo.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aisc.ngalo.R
import com.aisc.ngalo.completed.CompletedFragment
import com.aisc.ngalo.orders.OrdersOptions
import com.aisc.ngalo.orders.OrdersViewModel
import com.aisc.ngalo.rides.Rides
import com.aisc.ngalo.rides.RidesActivity
import com.aisc.ngalo.ui.theme.NgaloTheme

class AdminPanel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ordersViewModel by viewModels<OrdersViewModel>()
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Choose(ordersViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Choose(ordersViewModel: OrdersViewModel?) {
    val context = LocalContext.current
    ordersViewModel!!.orderCount()
    val count = ordersViewModel.observeorderCount().observeAsState()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Card(
                onClick = {
                    context.startActivity(Intent(context, AddBicyclePart::class.java))
                },
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .weight(1f)
                    .padding(5.dp),
                backgroundColor = colorResource(id = R.color.white)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bikeparts_upload),
                        contentDescription = "",
                        modifier = Modifier
                            .width(70.dp)
                            .height(60.dp)

                    )
                    Text(
                        text = "Upload Part",
                        modifier = Modifier
                            .padding(5.dp),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.ngalo_green)
                    )
                }
            }

            Card(
                onClick = {
                    context.startActivity(Intent(context, UploadBikeImages::class.java))
                },
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .weight(1f)
                    .padding(5.dp),
                backgroundColor = colorResource(id = R.color.white)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_upload_bike_photo),
                        contentDescription = "",
                        modifier = Modifier
                            .width(70.dp)
                            .height(60.dp)

                    )
                    Text(
                        text = "Upload Bike",
                        modifier = Modifier
                            .padding(5.dp),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.ngalo_green)
                    )
                }
            }

            Card(
                onClick = {
                    context.startActivity(Intent(context, CreateAdvert::class.java))
                },
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .weight(1f)
                    .padding(5.dp),
                backgroundColor = colorResource(id = R.color.white)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ad_upload),
                        contentDescription = "",
                        modifier = Modifier
                            .width(70.dp)
                            .height(60.dp)

                    )
                    Text(
                        text = "Create Ad",
                        modifier = Modifier
                            .padding(5.dp),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.ngalo_green)
                    )
                }
            }


        }

        Row(
            Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                onClick = {
                    context.startActivity(Intent(context, OrdersOptions::class.java))
                },
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(5.dp),
                backgroundColor = colorResource(id = R.color.white),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(Modifier.weight(1f)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_orders),
                            contentDescription = "",
                            modifier = Modifier
                                .width(70.dp)
                                .height(60.dp)
                        )
//                        Text(
//                            text = count.value.toString(),
//                            modifier = Modifier
//                                .padding(bottom = 15.dp)
//                                .align(Alignment.Center),
//                            fontSize = 16.sp,
//                            color = colorResource(id = R.color.white)
//
//                        )

                    }
                    Text(
                        text = "Orders",
                        modifier = Modifier
                            .padding(10.dp),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.ngalo_green)

                    )

                }
            }

            Card(
                onClick = {
                    context.startActivity(Intent(context, Rides::class.java))
                },
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(5.dp),
                backgroundColor = colorResource(id = R.color.white)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bike_rides),
                        contentDescription = "",
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp)

                    )
                    Text(
                        text = "Rides",
                        modifier = Modifier
                            .padding(10.dp),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.ngalo_green)
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    val ordersViewModel = OrdersViewModel()
    NgaloTheme {
        Choose(null)
    }
}