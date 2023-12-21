package com.aisc.ngalo.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.aisc.ngalo.BikesOptions
import com.aisc.ngalo.NgaloApplication
import com.aisc.ngalo.R
import com.aisc.ngalo.Purchase
import com.aisc.ngalo.helpers.LocationViewModel
import com.aisc.ngalo.helpers.calculateDistance
import com.aisc.ngalo.helpers.calculateTransportFare
import com.aisc.ngalo.ui.ui.theme.NgaloTheme
import com.aisc.ngalo.util.CurrencyUtil.formatCurrency
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ConfirmationActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationViewModel by viewModels<LocationViewModel>()
        val cartViewModel by viewModels<CartViewModel>()
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3(cartViewModel, locationViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting3(
    cartViewModel: CartViewModel,
    locationViewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    val id = FirebaseAuth.getInstance().currentUser!!.uid
    val cartItems by cartViewModel.fetchCartItems().observeAsState()
    val currentLocationName = locationViewModel.updateCurrentLocationName().observeAsState()
    val currentLocationCordinates =
        locationViewModel.updateCurrentLocationCordinates().observeAsState()
    val totalPrice by cartViewModel.getTotal().observeAsState()

    val currentLocationCoordinates: LiveData<String> =
        locationViewModel.updateCurrentLocationCordinates()
    val currentLocationCoordinatesState: String? by currentLocationCoordinates.observeAsState()

    val coordinates = currentLocationCordinates.value?.toString()?.split(",")
    var transportFares: Int? = null
    var grandTotal: Int? = null

    if (coordinates != null && coordinates.size >= 2) {
        val lat = coordinates[0].trim().toDoubleOrNull()
        val lng = coordinates[1].trim().toDoubleOrNull()

        if (lat != null && lng != null) {
            val distance = calculateDistance(lat, lng, 0.3765387, 32.6068885)
            transportFares = calculateTransportFare(distance.toInt())
            grandTotal = transportFares + totalPrice!!
        } else {
            // Handle the case when latitude or longitude is null
            // ...
        }
    } else {
        // Handle the case when coordinates are null or have insufficient size
        // ...
    }


//    distance = calculateDistance(lat, lng, 0.3765387, 32.6068885)
//    //val distance = calculateDistance(lat!!,lng!!, 0.3765387, 32.6068885)
//    val transportFares = calculateTransportFare(distance.toInt())
//
//    val grandTotal = transportFares + totalPrice!!


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f))
    ) {


        Text(
            text = "Payment",
            color = Color.Gray,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .fillMaxWidth()

        )

        var cashOnDelivery by remember { mutableStateOf(false) }

        Card(
            Modifier.padding(10.dp), backgroundColor = colorResource(id = R.color.white)
        ) {

            Row(Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = true,
                    onCheckedChange = { cashOnDelivery = it },
                    enabled = false,
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "Cash On Delivery",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable(onClick = { cashOnDelivery = !cashOnDelivery })
                        .padding(top = 10.dp, start = 4.dp)
                )
            }
        }

        Text(
            text = "PICKUP LOCATION",
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .fillMaxWidth()
        )

        var locationSelected by remember { mutableStateOf("Ngalo Shop") }

        Card(
            Modifier.padding(10.dp), backgroundColor = colorResource(id = R.color.white)
        ) {
            //val locationService = LocationService(LocalContext.current)
            val checked = false
            Column {
                Text(
                    fontSize = 16.sp,
                    text = "I will Pick it from:",
                    modifier = Modifier.padding(start = 10.dp),
                    color = colorResource(id = R.color.ngalo_green)
                )
                Row(Modifier.fillMaxWidth()) {

                    Checkbox(checked = locationSelected == "Ngalo Shop-(Kulambiro, Ring Road, " + "plot 3153)",
                        onCheckedChange = {
                            locationSelected = "Ngalo Shop-(Kulambiro, Ring Road, " + "plot 3153)"
                            transportFares = 0
                        })
                    Text(
                        text = "Ngalo Shop-(Kulambiro, Ring Road, " + "plot 3153)",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable(onClick = {
                                locationSelected =
                                    "Ngalo Shop-(Kulambiro, Ring Road, " + "plot 3153)"
                                transportFares = 0
                            })
                            .padding(start = 4.dp, top = 10.dp, end = 10.dp),
                        color = colorResource(id = R.color.ngalo_blue)
                    )
                }
                Text(
                    fontSize = 16.sp,
                    text = "Deliver it to:",
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    color = colorResource(id = R.color.ngalo_green)
                )

                Row(Modifier.fillMaxWidth()) {

                    Checkbox(checked = locationSelected == "${currentLocationName.value}",
                        onCheckedChange = { locationSelected = "${currentLocationName.value}" })
                    Text(
                        text = "My Location - (${currentLocationName.value})",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable(onClick = {
                                locationSelected = "${currentLocationName.value}"
                            })
                            .padding(start = 4.dp, top = 10.dp, end = 10.dp),
                        color = colorResource(id = R.color.ngalo_blue)
                    )

                }
            }
        }

        if (locationSelected == currentLocationName.value) {
            if (transportFares != null) {
                grandTotal = transportFares!! + totalPrice!!
            }
        } else {
            transportFares = 0
            grandTotal = totalPrice
        }

        Text(
            text = "SELECTED ITEMS",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .fillMaxWidth()
        )

        Card(
            Modifier.padding(10.dp), backgroundColor = colorResource(id = R.color.white)
        ) {
            LazyColumn {
                cartItems?.let {
                    items(it) { cartItem ->
                        OrdersItem(cartItem)
                        //Divider()
                    }
                }
                item {
                    //total price
                    Row {
                        Text(
                            text = "Total price:",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                            color = colorResource(id = R.color.ngalo_blue)
                        )
                        Column(
                            Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {

                            val currencyCode = "UGX"

                            val formattedPrice = if (totalPrice != null) {
                                formatCurrency(totalPrice!!, currencyCode)
                            } else {
                                "N/A"
                            }

                            Text(
                                text = formattedPrice,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 10.dp),
                                color = colorResource(id = R.color.ngalo_blue)
                            )
                        }
                    }

                    Row {
                        Text(
                            text = "Delivery fee:",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                            color = colorResource(id = R.color.ngalo_green)
                        )
                        Column(
                            Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {

                            val currencyCode = "UGX"
                            val formattedTransportFares =
                                if (locationSelected == currentLocationName.value) {
                                    if (transportFares != null) {
                                        formatCurrency(transportFares!!, currencyCode)
                                    } else {
                                        "N/A" // Provide a default value if grandTotal is null
                                    }
                                } else {
                                    "0"
                                }
                            Text(
                                text = formattedTransportFares,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 10.dp),
                                color = colorResource(id = R.color.ngalo_green)
                            )
                        }
                    }

                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, end = 10.dp, start = 10.dp),
                        color = colorResource(id = R.color.ngalo_green).copy(alpha = 0.3f)
                    )


                    Row {
                        Text(
                            text = "Grand Total:",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                            color = colorResource(id = R.color.ngalo_blue)
                        )
                        Column(
                            Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {

                            val currencyCode = "UGX"

                            val formattedGrantPrice =
                                grandTotal?.let { formatCurrency(it, currencyCode) }
                                    ?: "N/A" // Provide a default value if grandTotal is null

                            Text(
                                text = formattedGrantPrice,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 10.dp),
                                color = colorResource(id = R.color.ngalo_blue)
                            )
                        }
                    }


                    //confirm button
                    val openDialog = remember { mutableStateOf(false) }
                    //val coordinates = currentLocationCordinates.value!!.toString()

                    Column(
                        Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {

                        val context = LocalContext.current
                        Button(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(bottom = 10.dp),
                            onClick = {
                                openDialog.value = true
                            },
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = Color.White
//                            ),
                            border = BorderStroke(1.dp, colorResource(id = R.color.ngalo_green)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.ngalo_blue))
                        ) {
                            Text(text = "CONFIRM", color = colorResource(id = R.color.ngalo_blue))

                        }
                        if (openDialog.value) {
                            DisplayImageDialog(onDismiss = { openDialog.value = false }, onSave = {
                                saveToFirebase(
                                    id,
                                    cartItems,
                                    currentLocationName.value!!,
                                    "Cash on Delivery",
                                    locationSelected,
                                    coordinates.toString(),
                                    transportFares!!,
                                    grandTotal!!,
                                    System.currentTimeMillis()
                                )
                                openDialog.value = false
                                val uid = FirebaseAuth.getInstance().currentUser!!.uid

                                val removeCartRef =
                                    FirebaseDatabase.getInstance().reference.child("cartitems")
                                        .child(uid)
                                removeCartRef.removeValue().addOnCompleteListener {
                                    if (it.isComplete) {
                                        val con  = context as Activity
                                        con.finish()
                                        context.startActivity(Intent(context, BikesOptions::class.java))
                                    }
                                }
                            })
                        }
                    }
                }


            }
        }

    }
}


fun saveToFirebase(
    uid: String,
    cartItems: List<CartItem>?,
    locationSelected: String,
    cashOnDelivery: String,
    pickupLocation: String,
    coordinates: String?,
    tranpostFares: Int,
    grandTotal: Int,
    currentTimeMillis: Long
) {
    val id = FirebaseAuth.getInstance().currentUser!!.uid
    val purchases = Purchase(
        id,
        cartItems,
        locationSelected,
        "cash On Delivery",
        pickupLocation,
        coordinates.toString(),
        tranpostFares,
        grandTotal,
        currentTimeMillis
    )
    val purchaseRef = FirebaseDatabase.getInstance().reference.child("purchases")
    val usersRef =
        FirebaseDatabase.getInstance().reference.child("users").child(uid).child("purchases")
    purchaseRef.push().setValue(purchases)
    usersRef.push().setValue(purchases)

}


@Composable
fun DisplayImageDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.ngalo_blue)
                )
            ) {
                Text(text = "CANCEL", color = Color.White)
            }

            Button(
                onClick = onSave, colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.ngalo_green)
                )
            ) {
                Text(text = "OK", color = Color.White)
            }

        }
    }, icon = {
        // Display the image here
        Image(
            painter = painterResource(id = R.drawable.order_successfull),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize()
        )
    })

}


@Composable
fun OrdersItem(cartItem: CartItem) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {

            Text(
                text = cartItem.quantity.toString(),
                style = TextStyle(fontFamily = FontFamily(Font(R.font.josefinslab_bold))),
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp)
            )

            Text(
                text = "x",
                style = TextStyle(fontFamily = FontFamily(Font(R.font.josefinslab_bold))),
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp, end = 8.dp)
            )

            Text(
                text = cartItem.name!!,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.josefinslab_bold))),
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp)
            )
            Text(
                text = cartItem.category!!,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.josefinslab_bold))),
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp)
            )

            Text(
                text = "(${formatCurrency(cartItem.price!!, "UGX")})",
                fontSize = 18.sp,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.josefinslab_bold))),
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp)
            )

        }
    }

    Divider(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), color = Color.Gray.copy(alpha = 0.3f)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val cartItem = CartItem("1", "bike1", 2000000, null, 2, 1)
    NgaloTheme {
        OrdersItem(cartItem)
    }
}