package com.myapp.mobilecomputing.ui.home.homeReminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.myapp.mobilecomputing.R
import com.myapp.mobilecomputing.data.entity.Reminder
import com.myapp.mobilecomputing.data.repository.ReminderRepository
import com.myapp.mobilecomputing.ui.reminder.EditReminder
import com.myapp.mobilecomputing.util.viewModelProviderFactoryOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeReminder(
    modifier: Modifier = Modifier,
    navController : NavController
) {
    val viewModel: HomeReminderViewModel = viewModel(
        factory = viewModelProviderFactoryOf { HomeReminderViewModel() }
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        if (!viewState.reminders.isNullOrEmpty()) {
            ReminderList(
                list = viewState.reminders,
                navController = navController
            )
        }
    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>,
    navController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            ReminderListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
                navController = navController
            )
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: HomeReminderViewModel = viewModel(
        factory = viewModelProviderFactoryOf { HomeReminderViewModel() }
    )
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, message, paymentCategory, icon, editIcon, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // message
        Text(
            text = reminder.message,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(message) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // category
        /*Text(
            text = reminder.paymentCategory,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(paymentCategory) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(message.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )*/

        // date
        Text(
            text = when {
                reminder.reminderTime != null -> { reminder.reminderTime.toDateString() }
                else -> "No time set for the reminder"
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                //centerVerticallyTo(paymentCategory)
                top.linkTo(message.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )
        // Edit icon
        IconButton(
            onClick = {
                val reminderId : String = reminder.reminderId.toString()
                navController.navigate("edit_reminder/".plus(reminderId) )

            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(editIcon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(icon.start)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(R.string.edit)
            )
        }

        // icon
        IconButton(
            onClick = {
                      coroutineScope.launch {
                          viewModel.deleteReminder(reminder)
                      }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete)
            )
        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
}
fun Long.toDateString(): String {
    return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(this))

}