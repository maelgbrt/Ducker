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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip


/* val primary = Color(0xFF0000FF) //placeholder
val secondary = Color(0xFF8855FF) //placeholder
val white = Color(0xFFFFFFFF) //placeholder */

val PrimaryPurple = Color(0xFF7B2CBF)
val SecondaryPurple = Color(0xFF9D4EDD)
val BackgroundLight = Color(0xFFA5A5A5)

// Couleurs de texte et conteneurs
val CardBackground = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1C1B1F)
val TextSecondary = Color(0xFF625B71)



@Composable


fun SettingsItem(name: String, desc: String, onClick:() -> Unit ){
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(BackgroundLight) // Fond violet de la section
        .padding(8.dp)
        .clickable(onClick = {onClick()})
        ) {

        Image(
            painter = painterResource(id = R.drawable.leading_element), // Placeholder
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                /*.background(primary)*/
            .fillMaxWidth()
            .clip(RoundedCornerShape(48.dp))
            .padding(horizontal = 10.dp))
        {

            Text(name)
            Text(desc)}
    }
}

@Composable

fun SettingsPage(onNavigate: (Destination) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth(0.95f),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        //Button(onClick = onBackClick){
        //    Text(text = "Retour")
        //}

        Text(text = "Paramètres")
        SettingsItem("Mode Sombre", "Là tout de suite j'ai pas d'inspi", onClick = { onNavigate(Destination.HomePage)})
        SettingsItem("Mon compte", "Là tout de suite j'ai pas d'inspi", onClick = { onNavigate(Destination.HomePage)})
        SettingsItem("Changer frigo", "Là tout de suite j'ai pas d'inspi", onClick = { onNavigate(Destination.HomePage)})
        SettingsItem("Mes repas", "Là tout de suite j'ai pas d'inspi", onClick = { onNavigate(Destination.HomePage)})
    }
}


