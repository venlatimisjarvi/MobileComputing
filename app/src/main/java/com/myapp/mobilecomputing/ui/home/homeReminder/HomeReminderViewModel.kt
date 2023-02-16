package com.myapp.mobilecomputing.ui.home.homeReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.data.entity.Reminder
import com.myapp.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeReminderViewState())

    val state: StateFlow<HomeReminderViewState>
        get() = _state

    suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

    init {
        viewModelScope.launch {
                    reminderRepository.remindersForCreator(1).collect { list ->
                        _state.value = HomeReminderViewState(
                            reminders = list
                        )
                    }


        }
    }
}

data class HomeReminderViewState(
    val reminders: List<Reminder> = emptyList()
)