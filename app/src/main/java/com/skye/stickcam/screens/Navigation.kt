package com.skye.stickcam.screens

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(
            route = Screens.HomeScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(600))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(600))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(600))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(600))
            }
        ) {
            HomeScreen(onGalleryButtonClicked = {
                navController.navigate(Screens.GalleryScreen.route)
            })
        }

        composable(
            route = Screens.GalleryScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(600))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(600))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(600))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(600))
            }
        ) {
            GalleryScreen(onBackButtonClicked = {
                navController.popBackStack()
            })
        }
    }
}