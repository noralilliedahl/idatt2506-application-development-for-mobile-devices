package com.app.oving1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.oving1.ui.theme.Oving1Theme

class MainActivity : ComponentActivity() {

    private val TAG = "Nora"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "onCreate kalt")
        enableEdgeToEdge()
        setContent {
            Oving1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart kalt")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause kalt")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop kalt")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume kalt")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy kalt")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Oving1Theme {
        Greeting("Android")
    }
}