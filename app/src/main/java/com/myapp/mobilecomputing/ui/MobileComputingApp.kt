package com.myapp.mobilecomputing.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.mobilecomputing.ui.home.Home
import com.myapp.mobilecomputing.ui.login.Login
import com.myapp.mobilecomputing.ui.reminder.Payment
import com.myapp.mobilecomputing.ui.profile.Profile
import com.myapp.mobilecomputing.ui.register.Register

@Composable
fun MobileComputingApp(
    sharedPreferences: SharedPreferences,
    appState: MobileComputingAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController,
            sharedPreferences)
        }
        composable(route = "register") {
            Register(onBackPress = appState::navigateBack,
                navController = appState.navController)
        }
        composable(route = "home") {
            Home(
                navController = appState.navController
            )
        }
        composable(route = "payment") {
            Payment(onBackPress = appState::navigateBack)
        }
        composable(route = "profile") {
            Profile(onBackPress = appState::navigateBack,
                navController = appState.navController)
        }
    }
}