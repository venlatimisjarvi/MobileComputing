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
import com.myapp.mobilecomputing.data.entity.Reminder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun EditReminder(
    reminderId: Long,
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    navController: NavController
) {

    Surface {
        fun getReminder(id: Long) : Reminder{

            return runBlocking{ viewModel.getReminder(id)!! }
        }

        val reminder : Reminder = getReminder(reminderId)

        val coroutineScope = rememberCoroutineScope()
        val message = rememberSaveable { mutableStateOf(reminder.message) }
        val date = when {
            reminder.reminderTime != null -> { rememberSaveable { mutableStateOf(reminder.reminderTime.toDateString()) } }
            else -> rememberSaveable { mutableStateOf("") }
        }
        val time = when {
            reminder.reminderTime != null -> { rememberSaveable { mutableStateOf(reminder.reminderTime.toTimeString()) } }
            else -> rememberSaveable { mutableStateOf("") }
        }




        val latlng = navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<LatLng>("location_data")
            ?.value

        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

        fun getReminderTime() : Long?{

            if (date.value.isEmpty() || time.value.isEmpty()){
                return reminder.reminderTime
            }
            return formatter.parse(date.value + " " + time.value).time


        }

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
                Text(text = "Edit reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = {message.value = it},
                    label = { Text(text = "Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {

                    OutlinedTextField(
                        value = date.value,
                        onValueChange = { date.value = it },
                        label = { Text(text = "Date (dd.mm.yyyy)") },
                        modifier = Modifier.width(180.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = time.value,
                        onValueChange = { time.value = it },
                        label = { Text(text = "Time (hh:mm)") },
                        modifier = Modifier.width(180.dp)
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))
                if (latlng == null) {
                    OutlinedButton(
                        onClick = { navController.navigate("map") },
                        modifier = Modifier.height(55.dp)
                    ) {
                        Text(text = "Reminder location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {

                            viewModel.updateReminder(
                                Reminder(
                                    reminderId = reminder.reminderId,
                                    message = message.value,
                                    locationX = when{
                                        latlng?.latitude != null -> {latlng.latitude}
                                        else -> reminder.locationX},
                                    locationY = when{
                                        latlng?.longitude != null -> {latlng.latitude}
                                        else -> reminder.locationY},
                                    reminderTime = getReminderTime(),
                                    creationTime = reminder.creationTime,
                                    creatorId = 1,
                                    reminderSeen = false
                                            )

                            )

                        }
                        onBackPress()
                    },
                    modifier = Modifier.fillMaxWidth().size(55.dp)
                ) {
                    Text("Update reminder")
                }
            }
        }
    }
}
fun Long.toDateString(): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(this))

}
fun Long.toTimeString(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(this))

}
