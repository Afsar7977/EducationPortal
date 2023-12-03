package com.example.educationportal

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(pdfViewModel: PdfViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController, pdfViewModel = pdfViewModel)
        }
        composable(
            route = Screens.Course.route
        ) {
            CoursePdfView(navHostController = navController, pdfViewModel = pdfViewModel)
        }
    }
}