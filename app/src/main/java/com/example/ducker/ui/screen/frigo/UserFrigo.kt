package com.example.ducker.ui.screen.frigo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ducker.domain.model.Ingredient
import com.example.ducker.ui.navigation.Destination
import com.example.ducker.ui.viewmodel.UserFrigoViewModel

@Composable
fun UserFrigo(
    userId: String, // Reçu depuis ta navigation
    viewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit
) {
    val frigoWithIngredients by viewModel.frigoWithIngredients.collectAsState()

    val ingredientsList = frigoWithIngredients?.ingredients ?: emptyList()


    fun addLike(userId: String){
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Frigo de l'utilisateur : $userId",
            style = MaterialTheme.typography.titleLarge
        )

        // Affichage des informations du frigo (basique avec toString ou personnalisé)

//        Image(
//            painter = rememberAsyncImagePainter(model = frigoWithIngredients?.photo),
//            contentDescription = "Photo de profil",
//            modifier = Modifier
//                .width(100.dp)
//                .height(100.dp)
//        )
        Text(
            text = "J'essaye ça : ${frigoWithIngredients?.frigo?.userId ?: "Chargement ou pas de frigo trouvé..."}"
        )



        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Red)
        ){
            Column() {
                Text(
                    text = "Test pour ce qui est des ingredients"
                )
                for (ingredient in ingredientsList){
                    Card() {
                        Text(text = ingredient.nomIngredients)
                        Text(text = ingredient.datePeremptionIngredients)

                    }
                }
            }

        }
        Box(){
            Row() {
                Button(
                    onClick = {viewModel.vote("usr_001",false)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red)
                ) {
                    Text(text = "Je dislike")
                }
                Button(
                    onClick = {
                        viewModel.vote("usr_001",true)
                        onNavigate(Destination.UsersPage)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green)
                ) {
                    Text(text = "Je like")
                }
            }
        }
    }
}