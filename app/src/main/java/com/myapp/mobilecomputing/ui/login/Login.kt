package com.myapp.mobilecomputing.ui.login

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.data.entity.User
import com.myapp.mobilecomputing.data.repository.UserRepository
import com.myapp.mobilecomputing.ui.MainActivity
import com.myapp.mobilecomputing.ui.register.RegisterViewModel


@Composable
fun Login(
    navController: NavController,
    sharedPreferences: SharedPreferences,
) {


    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    checkUser(username.value, password.value, sharedPreferences, navController)
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth().size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Don't have an account yet?",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(5.dp)
                    .heightIn(max = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { navController.navigate("register") },
                enabled = true,
                modifier = Modifier.fillMaxWidth().size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Register")
            }
        }
    }
}
    private fun checkUser(username: String, password: String, sharedPreferences: SharedPreferences, navController: NavController ){
        val correctUsername = sharedPreferences.getString("username", "")
        val correctPassword = sharedPreferences.getString("password", "")

        if(username.equals(correctUsername) && password.equals(correctPassword)){
            navController.navigate("home")
        }
        /*val user: User? = Graph.userRepository.getUserWithUsername(username)
        if (user != null){
            if(user.password == password){
                navController.navigate("home")
            }
        }*/
    }
