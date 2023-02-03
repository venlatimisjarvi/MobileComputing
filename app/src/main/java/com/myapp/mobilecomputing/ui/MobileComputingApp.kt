package com.codemave.mobilecomputing.ui

import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codemave.mobilecomputing.ui.MobileComputingAppState
import com.codemave.mobilecomputing.ui.rememberMobileComputingAppState
import com.codemave.mobilecomputing.ui.home.Home
import com.codemave.mobilecomputing.ui.login.Login
import com.codemave.mobilecomputing.ui.payment.Payment
import com.codemave.mobilecomputing.ui.profile.Profile
import android.content.SharedPreferences
import com.codemave.mobilecomputing.ui.register.Register

@Composable
fun MobileComputingApp(
    appState: MobileComputingAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController)
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