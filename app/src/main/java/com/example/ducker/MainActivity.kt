package com.example.ducker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ducker.ui.theme.DuckerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuckerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HelloWorld(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable

fun HelloWorld(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Hello World")
        Button( onClick = {}) {
            Text(text = "Clique Me")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HelloWorldPreview() {
    DuckerTheme {
        HelloWorld()
    }
}