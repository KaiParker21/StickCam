package com.skye.stickcam.screens

sealed class Screens(val route: String) {
    object GalleryScreen: Screens("gallery screen")
    object HomeScreen: Screens("home screen")
}