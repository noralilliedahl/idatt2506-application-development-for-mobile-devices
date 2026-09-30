package com.app.oving4.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.oving4.Friend
import com.app.oving4.ui.theme.Oving4Theme

@Composable
fun FriendItem(
    friend: Friend,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    ) {
        Text(text = friend.name)
        Text(text = friend.birthday)
    }
}

@Preview(showBackground = true)
@Composable
private fun FriendItemPreview() {
    Oving4Theme {
        FriendItem(
            friend = Friend(name = "Navn Etternavn", birthday = "12.12.2002"),
            onClick = {}
        )
    }
}
