package com.myapp.mobilecomputing.ui.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.maps.model.LatLng



@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    navController: NavController
) {
    Surface {
        val coroutineScope = rememberCoroutineScope()
        val message = rememberSaveable { mutableStateOf("") }
        val reminderTime = rememberSaveable { mutableStateOf("") }

        val latlng = navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<LatLng>("location_data")
            ?.value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Reminder title")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row{
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Date")},
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Time")},
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    if (latlng == null) {
                        OutlinedButton(
                            onClick = { navController.navigate("map") },
                            modifier = Modifier.height(55.dp)
                        ) {
                            Text(text = "Payment location")
                        }
                    } else {
                        Text(
                            text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth().size(55.dp)
                ) {
                    Text("Save reminder")
                }
            }
        }
    }
}