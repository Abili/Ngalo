package com.aisc.ngalo.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object MainActivity: Screens("home_screen")
    object HireBike: Screens("hire_bike")
    object RepairBike: Screens("repaire_bike")
    object BuyBike: Screens("buy_bike")
    object SignUp: Screens("sign_up")
    object AdminOrUser: Screens("admin_or_user")
    object AuthUiActivity : Screens("auth_ui_activity")
}