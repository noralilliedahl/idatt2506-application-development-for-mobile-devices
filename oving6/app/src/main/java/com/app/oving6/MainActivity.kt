package com.app.oving6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.oving6.ui.ChatScreenWithViewModel
import com.app.oving6.ui.theme.Oving6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oving6Theme {
                ChatScreenWithViewModel()
            }
        }
    }
}
