package com.myapp.mobilecomputing.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapp.mobilecomputing.ui.home.Home
import com.myapp.mobilecomputing.ui.login.Login
import com.myapp.mobilecomputing.ui.maps.ReminderLocationMap
import com.myapp.mobilecomputing.ui.reminder.Reminder
import com.myapp.mobilecomputing.ui.profile.Profile
import com.myapp.mobilecomputing.ui.register.Register
import com.myapp.mobilecomputing.ui.reminder.EditReminder

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
        composable("edit_reminder/{reminderId}",
            arguments = listOf(navArgument("reminderId") { type = NavType.StringType })) {
            backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("reminderId")
            if (reminderId != null) {
                EditReminder(
                    reminderId = reminderId.toLong(),
                    onBackPress = appState::navigateBack,
                    navController = appState.navController
                )
            }
            else {
                appState.navController.navigate("home")
            }
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