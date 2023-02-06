package com.myapp.mobilecomputing.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.ui.theme.MobileComputingTheme

class MainActivity : ComponentActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var testName: String = "Venla"
    var testPassword: String = "salasana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Graph.provide(this)
        setContent {
            sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", testName)
            editor.putString("password", testPassword)
            editor.apply()
            MobileComputingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MobileComputingApp(sharedPreferences)
                }
            }
        }
    }
}
