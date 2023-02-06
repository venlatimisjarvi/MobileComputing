package com.myapp.mobilecomputing.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.myapp.mobilecomputing.ui.home.HomeAppBar
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Profile(
    onBackPress: () -> Unit,
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = "Venla"
        val password = "passwd"

        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

            HomeAppBar(
                navController = navController,
                backgroundColor = appBarColor,
            )
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            /*OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )*/
            Text(
                text = "My username",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(5.dp)
                    .heightIn(max = 20.dp)
            )


            Text(
                text = username,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(5.dp)
                    .heightIn(max = 60.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Edit profile")
            }
        }
    }
}