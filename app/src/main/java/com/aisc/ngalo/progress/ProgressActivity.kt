package com.aisc.ngalo.progress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aisc.ngalo.R
import com.aisc.ngalo.ui.theme.NgaloTheme

class ProgressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressViewModel by viewModels<ProgressViewModel>()
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Progress("progress", progressViewModel)
                }
            }
        }
    }
}

@Composable
fun Progress(txt: String, progressViewModel: ProgressViewModel?, modifier: Modifier = Modifier) {
    var progressSelected by remember { mutableStateOf("Received") }
    val progress = progressViewModel?.orderRecieved()?.observeAsState()
    val ready = progressViewModel?.orderReady()?.observeAsState()
    // val enabled by remember { mutableStateOf(false) }

    Column(modifier.fillMaxSize()) {
        Text(
            text = "Progress",
            modifier = modifier.padding(start = 10.dp, top = 10.dp),
            fontSize = 26.sp,
            color = colorResource(id = R.color.ngalo_blue)
        )

        Column {
            Row {
                Text(
                    text = ready!!.value!!.category!!,
                    modifier = modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .weight(1f),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.ngalo_blue)
                )

                Text(
                    text = ready.value!!.requestTime!!,
                    modifier = modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.ngalo_blue)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ngalo_logo),
                modifier = Modifier
                    .height(350.dp)
                    .width(400.dp),
                contentDescription = null, alignment = Alignment.Center
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColor = if (progress?.value == "Received") Color.Red.copy(alpha = 0.3f) else Color.Gray.copy(
                    alpha = 0.3f
                )
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "(${progress?.value})",
                        fontSize = 26.sp,
                        modifier = Modifier
                            .clickable(onClick = {
                                progressSelected = progress?.value ?: ""
                            })
                            .padding(start = 4.dp, end = 10.dp),
                        color = if (progress?.value == "Received") colorResource(id = R.color.ngalo_blue) else Color.Gray.copy(
                            alpha = 0.3f
                        )
                    )
                }
            }

        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColor = if (progress?.value == "Received") Color.Yellow.copy(alpha = 0.3f) else Color.Gray.copy(
                    alpha = 0.3f
                )
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "(${progress?.value})",
                        fontSize = 26.sp,
                        modifier = Modifier
                            .clickable(onClick = {
                                progressSelected = progress?.value ?: ""
                            })
                            .padding(start = 4.dp, end = 10.dp),
                        color = if (progress?.value == "Preparing") colorResource(id = R.color.ngalo_blue) else Color.Gray.copy(
                            alpha = 0.3f
                        )
                    )
                }
            }

        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColor = if (progress?.value == "Received") Color.Green.copy(alpha = 0.3f) else Color.Gray.copy(
                    alpha = 0.3f
                )
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "(${progress?.value})",
                        fontSize = 26.sp,
                        modifier = Modifier
                            .clickable(onClick = {
                                progressSelected = progress?.value ?: ""
                            })
                            .padding(start = 4.dp, end = 10.dp),
                        color = if (progress?.value == "Ready") colorResource(id = R.color.ngalo_blue) else Color.Gray.copy(
                            alpha = 0.3f
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressPreview2() {

    NgaloTheme {
        Progress("progress", null)
    }
}