package com.example.ducker

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Page2(onBackClick: () -> Unit) {
    Column() {
        Text(text = "Seconde page")
        Button(onClick = onBackClick){
            Text(text = "Retour")

        }
    }
}