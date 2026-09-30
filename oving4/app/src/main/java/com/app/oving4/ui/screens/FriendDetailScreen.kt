package com.app.oving4.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.oving4.Friend
import com.app.oving4.R

@Composable
fun FriendDetail(
    friend: Friend,
    onEditClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = friend.name)
        Text(text = friend.birthday)

        Button(onClick = onEditClick) {
            Text(text = stringResource(R.string.edit_friend))
        }

        Button(onClick = onBack) {
            Text(text = stringResource(R.string.back_button))
        }
    }
}
