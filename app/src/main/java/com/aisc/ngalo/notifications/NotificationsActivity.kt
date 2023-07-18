package com.aisc.ngalo.notifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.aisc.ngalo.R
import com.aisc.ngalo.notifications.ui.theme.NgaloTheme

class NotificationsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgaloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}
@Composable
fun NotifMessage(notification: Notification){
    LazyColumn{
        notification.let {
            item(it) {
                NotifItem(it)
            }
        }
    }
}

@Composable
fun NotifItem(notification: Notification) {
    Row() {
        Image(
            painter = painterResource(id = R.drawable.ngalo_logo),
            contentDescription = "notification_icon"
        )
        Text(
            text = notification.content!!,
            modifier = Modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    val notification = Notification("This is notification")
    NgaloTheme {
        NotifItem(notification)
    }
}