package com.example.educationportal

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "course_screen?value={pdfUrl}",
            arguments = listOf(navArgument("pdfUrl") {
                type = NavType.StringType
                defaultValue = "https://myreport.altervista.org/Lorem_Ipsum.pdf"
            })
        ) { backStackEntry ->
            CoursePdfView(
                navHostController = navController,
                pdfUrl = backStackEntry.arguments?.getString("pdfUrl")
            )
        }
    }
}