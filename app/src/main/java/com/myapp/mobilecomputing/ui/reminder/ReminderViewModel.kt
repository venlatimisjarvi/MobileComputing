package com.myapp.mobilecomputing.ui.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.data.entity.Reminder
import com.myapp.mobilecomputing.data.repository.ReminderRepository
import com.myapp.mobilecomputing.util.NotificationWorker
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.myapp.mobilecomputing.R
import com.myapp.mobilecomputing.ui.MainActivity
import kotlinx.coroutines.coroutineScope
import java.util.*

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    //private val _state = MutableStateFlow(ReminderViewState())

    //val state: StateFlow<ReminderViewState>
        //get() = _state

    suspend fun saveReminder(reminder: Reminder): Long {
        createReminderMadeNotification(reminder)
        setReminderNotification(reminder)
        return reminderRepository.addReminder(reminder)
    }

    suspend fun updateReminderAndNotify(reminder: Reminder) {
        createReminderMadeNotification(reminder)
        setReminderNotification(reminder)
        return reminderRepository.updateReminder(reminder)
    }
    suspend fun updateReminder(reminder: Reminder){
        return reminderRepository.updateReminder(reminder)
    }

    suspend fun getReminder(reminderId : Long) : Reminder?{
        return reminderRepository.reminder(reminderId)
    }
    init {
        createNotificationChannel(context = Graph.appContext)
        viewModelScope.launch {

        }
    }

    private fun setOneTimeNotification() {
        val workManager = WorkManager.getInstance(Graph.appContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(notificationWorker)

        //Monitoring for state of work
        workManager.getWorkInfoByIdLiveData(notificationWorker.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    createSuccessNotification()
                } else {
                    createErrorNotification()
                }
            }
    }
    private fun setReminderNotification(reminder : Reminder) {
        val workManager = WorkManager.getInstance(Graph.appContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val todayDateTime = Calendar.getInstance()
        val delayInSeconds = (reminder.reminderTime!!/1000L) - (todayDateTime.timeInMillis/1000L)

        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(notificationWorker)

        //Monitoring for state of work
        workManager.getWorkInfoByIdLiveData(notificationWorker.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {

                    viewModelScope.launch {

                        updateReminder(Reminder(
                            reminderId = reminder.reminderId,
                            message = reminder.message,
                            locationX = reminder.locationX,
                            locationY = reminder.locationY,
                            reminderTime = reminder.reminderTime,
                            creationTime = reminder.creationTime,
                            creatorId = reminder.creatorId,
                            reminderSeen = true
                        ))

                    }
                    createReminderNotification(reminder)
                }
            }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NotificationChannelName"
            val descriptionText = "NotificationChannelDescriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createSuccessNotification() {
        val notificationId = 1
        val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Success! Download complete")
            .setContentText("Your countdown completed successfully")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            //notificationId is unique for each notification that you define
            notify(notificationId, builder.build())
        }
    }

    private fun createErrorNotification() {
        val notificationId = 1
        val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Error! Download incomplete")
            .setContentText("Your countdown encountered an error and stopped abruptly")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            //notificationId is unique for each notification that you define
            notify(notificationId, builder.build())
        }
    }


    private fun createReminderMadeNotification(reminder : Reminder) {
        val notificationId = 2
        val intent = Intent(Graph.appContext, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //val pendingIntent = PendingIntent.getActivity(Graph.appContext,0,intent,0)
        val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New reminder set")
            .setContentText("New reminder set on ${reminder.reminderTime!!.toDateString()} ${reminder.reminderTime!!.toTimeString()}")
            //.setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(Graph.appContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createReminderNotification(reminder : Reminder) {
        val notificationId = 3
        val intent = Intent(Graph.appContext, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //val pendingIntent = PendingIntent.getActivity(Graph.appContext,0,intent,0)
        val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Reminder")
            .setContentText("${reminder.message}")
            //.setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(Graph.appContext)) {
            notify(notificationId, builder.build())
        }
    }

}

/*data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)*/