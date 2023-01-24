package com.aisc.ngalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aisc.ngalo.navigation.NavGraph
import com.aisc.ngalo.navigation.Screens
import com.aisc.ngalo.ui.theme.NgaloTheme
import kotlinx.coroutines.delay
import java.time.Duration

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgaloTheme {
                //var currentScreen: Des by remember { mutableStateOf(SplashScreen) }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultPreview2()

                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    // AnimationEffect
    //context.finish()

    //context.startActivity(Intent(context, MainActivity::class.java))

    // Image
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ngalobg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }

}

private fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(0) {
            saveState = true
            //inclusive = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NgaloTheme {
        val navController = rememberNavController()
        SplashScreen(navController)
        NavGraph(navController = navController)
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }