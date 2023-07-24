package com.aisc.ngalo.navigation

interface Destinations {
    val route: String
}

object SplashScreen:Destinations {
    override val route: String = "splash_screen"

}
object MainActivity:Destinations {
    override val route: String = "main_activity"

}
object AdminOUser:Destinations {
    override val route: String = "admin_or_user"

}
object SignUp:Destinations {
    override val route: String = "sign_up"

}
object HireBike:Destinations {
    override val route: String = "hire_bike"

}
object singleScreen:Destinations {
    const val whichPage = "which_page"
    override val route: String = "single_screen"
    val routeWithArgs = "$route/{$whichPage}"
}