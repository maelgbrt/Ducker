package com.example.ducker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

@Composable


fun SettingsItem(name: String, desc: String){
    Row() {
        Image(
            painter = painterResource(id = R.drawable.leading_element), // Placeholder
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column() {
            Text(name)
            Text(desc)}
    }
}

@Composable
fun SettingsPage(onBackClick: () -> Unit) {


    Column() {
        Button(onClick = onBackClick){
            Text(text = "Retour")
        }

        Text(text = "Paramètres")
        SettingsItem("Mode Sombre", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Mon compte", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Changer frigo", "Là tout de suite j'ai pas d'inspi")
        SettingsItem("Mes repas", "Là tout de suite j'ai pas d'inspi")
    }
}


