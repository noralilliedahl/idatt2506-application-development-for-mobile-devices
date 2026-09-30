package com.app.oving3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.oving3.ui.theme.Oving3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oving3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    Oving3Theme {
        Screen()
    }
}

@Composable
fun Screen(modifier: Modifier = Modifier) {

    var name by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var friends by remember { mutableStateOf(listOf<Friend>()) }

    Column(modifier = modifier
        .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.title))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name)) }
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text(stringResource(R.string.birthday)) }
        )

        Button(
            onClick = {
                friends = friends + Friend(name, birthday)
                name = ""
                birthday = ""
            }
        ) {
            Text(text = stringResource(R.string.add_friend_button))
        }

        LazyColumn {
            items(friends) { friend ->
                FriendItem(friend = friend)
            }
        }
    }
}

@Composable
fun FriendItem(friend: Friend, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(text = friend.name)
        Text(text = friend.birthday)
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    Oving3Theme {
        FriendItem(friend = Friend("Kari Nordmann", "01.01.2000"))
    }
}