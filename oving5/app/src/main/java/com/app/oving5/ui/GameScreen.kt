package com.app.oving5.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.oving5.R
import com.app.oving5.ui.theme.Oving5Theme

@Composable
fun GameScreen(
    state: GameState,
    onNameChange: (String) -> Unit,
    onCardNumChange: (String) -> Unit,
    onGuessChange: (String) -> Unit,
    onStartGame: () -> Unit,
    onGuess: () -> Unit,
    onStartOver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { GameTopBar(onStartOver = onStartOver) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!state.started) {
                InputField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = R.string.name
                )
                InputField(
                    value = state.cardNum,
                    onValueChange = onCardNumChange,
                    label = R.string.card_number,
                    keyboardType = KeyboardType.Number
                )
                Button(onClick = onStartGame) {
                    Text(stringResource(R.string.start))
                }
            } else {
                InputField(
                    value = state.guess,
                    onValueChange = onGuessChange,
                    label = R.string.guess,
                    keyboardType = KeyboardType.Number
                )
                Button(onClick = onGuess) {
                    Text(stringResource(R.string.send_guess))
                }
            }

            // All replies from the server
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(state.responses) { response ->
                    Text(response, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(onStartOver: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(R.string.title)) },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = stringResource(R.string.menu)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.new_game)) },
                    onClick = {
                        expanded = false
                        onStartOver()
                    }
                )
            }
        }
    )
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    Oving5Theme {
        GameScreen(
            state = GameState(name = "Test Testesen", cardNum = "12345"),
            onNameChange = {}, onCardNumChange = {}, onGuessChange = {},
            onStartGame = {}, onGuess = {}, onStartOver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    Oving5Theme {
        GameScreen(
            state = GameState(
                started = true,
                responses = listOf("Oppgi et tall mellom 1 og 10!", "Tallet 5 er for lite!")
            ),
            onNameChange = {}, onCardNumChange = {}, onGuessChange = {},
            onStartGame = {}, onGuess = {}, onStartOver = {}
        )
    }
}
