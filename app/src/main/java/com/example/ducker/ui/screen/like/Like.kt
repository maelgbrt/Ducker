package com.example.ducker.ui.screen.like

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.room.util.TableInfo
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.navigation.Destination
import com.example.ducker.ui.viewmodel.LikeViewModel

@Composable
fun Like(
    onNavigate: (Destination) -> Unit,
    viewModel: LikeViewModel
){
    val Userlike by viewModel.likersNames.collectAsState()

    Box(

    ){
        Column(){
            Text(text = "Les Favoris")

            for (user in Userlike){
                Card() {
                    Text(text = user.prenom)
                    Text(text = user.localisation)
                    Button(onClick = {onNavigate(Destination.FrigoUser(userId = user.id))}) {
                        Text(text = "Voir Frigo")
                    }
                }
            }
        }

    }

}