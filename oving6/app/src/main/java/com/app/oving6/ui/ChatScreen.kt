package com.app.oving6.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.oving6.R
import com.app.oving6.ui.theme.Oving6Theme

@Composable
fun ChatScreenWithViewModel(viewModel: ChatViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var draft by rememberSaveable { mutableStateOf("") }

    ChatScreen(
        state = state,
        draft = draft,
        onDraftChange = { draft = it },
        onStartAsServer = viewModel::startAsServer,
        onStartAsClient = viewModel::startAsClient,
        onSend = {
            viewModel.send(draft)
            draft = ""
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    draft: String,
    onDraftChange: (String) -> Unit,
    onStartAsServer: () -> Unit,
    onStartAsClient: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(topBarTitle(state)) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .padding(16.dp)
        ) {
            if (state.isServer == null) {
                RoleChoice(onStartAsServer, onStartAsClient)
            } else {
                MessageList(state.messages, Modifier.weight(1f))
                MessageInput(draft, onDraftChange, onSend)
            }
        }
    }
}

@Composable
private fun topBarTitle(state: ChatState): String {
    val role = when (state.isServer) {
        null -> return stringResource(R.string.title)
        true -> stringResource(R.string.server)
        false -> stringResource(R.string.client)
    }
    return stringResource(R.string.role_and_connected, role, state.connected)
}

@Composable
private fun RoleChoice(onStartAsServer: () -> Unit, onStartAsClient: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(stringResource(R.string.choose_role))
        Button(onClick = onStartAsServer) { Text(stringResource(R.string.be_server)) }
        Button(onClick = onStartAsClient) { Text(stringResource(R.string.connect)) }
    }
}

@Composable
private fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(messages) { message ->
            val text = when {
                message.goodbye -> stringResource(R.string.someone_left)
                message.byMe -> stringResource(R.string.by_me, message.contents)
                else -> stringResource(R.string.by_other, message.contents)
            }
            Text(
                text = text,
                textAlign = if (message.byMe) TextAlign.End else TextAlign.Start,
                style = if (message.goodbye) MaterialTheme.typography.labelMedium
                        else MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(stringResource(R.string.message)) },
            singleLine = true,   // linjeskift ville blitt to meldinger
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onSend, enabled = value.isNotBlank()) {
            Text(stringResource(R.string.send))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    Oving6Theme {
        ChatScreen(
            state = ChatState(
                isServer = true,
                connected = 2,
                messages = listOf(
                    Message(byMe = false, contents = "Hei fra klient 1"),
                    Message(byMe = true, contents = "Hei tilbake!"),
                    Message(byMe = false, contents = "", goodbye = true)
                )
            ),
            draft = "Skriver …",
            onDraftChange = {}, onStartAsServer = {}, onStartAsClient = {}, onSend = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoleChoicePreview() {
    Oving6Theme {
        ChatScreen(
            state = ChatState(),
            draft = "",
            onDraftChange = {}, onStartAsServer = {}, onStartAsClient = {}, onSend = {}
        )
    }
}
