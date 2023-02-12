package com.myapp.mobilecomputing.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.mobilecomputing.ui.home.Home
import com.myapp.mobilecomputing.ui.login.Login
import com.myapp.mobilecomputing.ui.maps.ReminderLocationMap
import com.myapp.mobilecomputing.ui.reminder.Reminder
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
        composable(route = "reminder") {
            Reminder(onBackPress = appState::navigateBack,
            navController = appState.navController)
        }
        composable(route = "profile") {
            Profile(onBackPress = appState::navigateBack,
                navController = appState.navController)
        }
        composable(route = "map") {
            ReminderLocationMap(navController = appState.navController)
        }
    }
}