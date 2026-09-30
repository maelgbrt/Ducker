package com.example.ducker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement


val primary = Color(0xFF0000FF) //placeholder
val secondary = Color(0xFF8855FF) //placeholder

@Composable


fun SettingsItem(name: String, desc: String){
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth().background(primary)) {
        Image(
            painter = painterResource(id = R.drawable.leading_element), // Placeholder
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.background(secondary).fillMaxWidth()) {
            Text(name)
            Text(desc)}
    }
}

@Composable

fun SettingsPage(onNavigate: (Destination) -> Unit) {
    Column(modifier = Modifier.width(200.dp)) {
        //Button(onClick = onBackClick){
        //    Text(text = "Retour")
        //}

        Text(text = "Paramètres")
        SettingsItem("Mode Sombre", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Mon compte", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Changer frigo", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Mes repas", "Là tout de suite j'ai pas d'inspi")
    }
}


