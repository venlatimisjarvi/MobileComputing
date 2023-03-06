package com.myapp.mobilecomputing.ui.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.widget.DatePicker
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.myapp.mobilecomputing.Graph
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.Calendar.*


@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    navController: NavController
) {
    Surface {
        val coroutineScope = rememberCoroutineScope()

        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(YEAR)
        val mMonth = mCalendar.get(MONTH)
        val mDay = mCalendar.get(DAY_OF_MONTH)
        val mHour = mCalendar[HOUR_OF_DAY]
        val mMinute = mCalendar[MINUTE]

        mCalendar.time = Date()

        val calendar = rememberSaveable {(Calendar.getInstance())}
        val mTime = rememberSaveable { mutableStateOf("") }
        val mDate = rememberSaveable { mutableStateOf("") }
        val message = rememberSaveable { mutableStateOf("") }

        val mDatePickerDialog = DatePickerDialog(
            Graph.appContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                calendar.set(mYear, mMonth, mDayOfMonth)
            }, mYear, mMonth, mDay
        )
        val mTimePickerDialog = TimePickerDialog(
            Graph.appContext,
            {_, mHour : Int, mMinute: Int ->
                calendar.set(HOUR_OF_DAY, mHour)
                calendar.set(MINUTE, mMinute)
                mTime.value = "$mHour:$mMinute"
            }, mHour, mMinute, false
        )


        val latlng = navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<LatLng>("location_data")
            ?.value

         val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")

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
                    value = message.value,
                    onValueChange = {message.value = it},
                    label = { Text(text = "Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))


                Button(
                    onClick = {
                        mDatePickerDialog.show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(0.6f)),
                    modifier = Modifier.height(55.dp)
                ) {
                    Text("Open Date Picker", color = MaterialTheme.colors.onPrimary)
                }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Selected Date: ${mDate.value}",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(10.dp))

                Button(
                    onClick = { mTimePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(0.6f)),
                    modifier = Modifier.height(55.dp)
                ) {
                    Text(text = "Open Time Picker", color = MaterialTheme.colors.onPrimary)
                }

                Spacer(modifier = Modifier.size(10.dp))

                    Text(text = "Selected Time: ${mTime.value}", fontSize = 13.sp)

                /*Row(horizontalArrangement = Arrangement.SpaceEvenly) {
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
                }*/

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
                            viewModel.saveReminder(
                                com.myapp.mobilecomputing.data.entity.Reminder(
                                    message = message.value,
                                    locationX = latlng?.latitude,
                                    locationY = latlng?.longitude,
                                    reminderTime = calendar.timeInMillis,
                                    //formatter.parse(mDate.value + " " + mTime.value).time,
                                    creationTime = Date().time,
                                    creatorId = 1,
                                    reminderSeen = false
                                )
                            )
                        }
                        onBackPress()
                    },
                    modifier = Modifier.fillMaxWidth().size(55.dp)
                ) {
                    Text("Save reminder")
                }
            }
        }
    }
}

