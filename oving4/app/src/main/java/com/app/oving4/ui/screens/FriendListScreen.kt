package com.app.oving4.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.oving4.Friend
import com.app.oving4.R
import com.app.oving4.ui.components.FriendItem

@Composable
fun FriendList(
    friends: List<Friend>,
    onFriendClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = stringResource(R.string.friends_list))
        Button(
            onClick = onAddClick) {
            Text(text = stringResource(R.string.add_friend))
        }

        LazyColumn {
            itemsIndexed(friends) {
                index, friend -> FriendItem(
                    friend = friend,
                    onClick = { onFriendClick(index) }
                )
            }
        }
    }
}
