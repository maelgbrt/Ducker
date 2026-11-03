package com.example.ducker.ui.screen.ingredientAjout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ducker.ui.navigation.Destination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientAjout(
    onNavigate : (Destination) -> Unit,

) {

    var description by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var datePeremption by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
        TopAppBar(
            title = { Text(text = "Ajouter ingrédients") },
            navigationIcon = {
                TextButton(onClick = { onNavigate(Destination.HomePage) }) {
                    Text(text = "Retour")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    },) {
        paddingValues ->
        Column() {
            Text(
                text = name,
                modifier = Modifier.padding(paddingValues)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = datePeremption,
                onValueChange = {datePeremption = it},
                label = { Text("Date de péremption")
                },
                modifier = Modifier.fillMaxWidth()
            )
                Button(onClick = { },
                    modifier = Modifier.fillMaxSize()) {
                    Text(text = "Ajouter un ingrédient")
                }

            }
        }



    }
