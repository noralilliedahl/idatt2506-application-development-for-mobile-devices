package com.app.oving4.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.oving4.R

@Composable
fun FriendForm(
    name: String,
    birthday: String,
    buttonText: String,
    onNameChange: (String) -> Unit,
    onBirthdayChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.name)) }
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = onBirthdayChange,
            label = { Text(stringResource(R.string.birthday)) }
        )

        Button(
            onClick = onSubmit
        ) {
            Text(text = buttonText)
        }

        Button(onClick = onBack) {
            Text(text = stringResource(R.string.back_button))
        }
    }
}
