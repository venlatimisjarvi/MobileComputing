package com.myapp.mobilecomputing.ui.reminder

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.data.entity.Reminder
import com.myapp.mobilecomputing.data.repository.ReminderRepository
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    //private val _state = MutableStateFlow(ReminderViewState())

    //val state: StateFlow<ReminderViewState>
        //get() = _state

    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        return reminderRepository.updateReminder(reminder)
    }

    suspend fun getReminder(reminderId : Long) : Reminder?{
        return reminderRepository.reminder(reminderId)
    }
    init {

        viewModelScope.launch {

        }
    }
}

/*data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)*/