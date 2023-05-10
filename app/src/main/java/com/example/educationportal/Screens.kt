package com.example.educationportal

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Home : Screens("home_screen")
    object Course : Screens("course_screen")
    //object Gallery : Screens("photo_gallery_screen")
}