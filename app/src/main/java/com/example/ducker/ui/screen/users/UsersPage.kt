package com.example.ducker.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ducker.ui.navigation.Destination
import com.example.ducker.ui.DuckerViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import com.example.ducker.ui.viewmodel.LikeViewModel

@Composable
fun UsersPage(
    viewModel: DuckerViewModel,
    onNavigate: (Destination) -> Unit,
    likeViewModel: LikeViewModel, // Tu ajoutes ton LikeViewModel ici
) {


    val users by viewModel.users.collectAsState()
    val error by viewModel.error.collectAsState()
    val votedIds by likeViewModel.votedUserIds.collectAsState() // On récupère les likes depuis le bon ViewModel

    val filteredUsers = users.filter { user -> !votedIds.contains(user.id) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des SSSilisateurs",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Total Users de l'API: ${users.size}")
        Text(text = "IDs Votés/Filtrés: $votedIds")

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = filteredUsers, key = { it.hashCode() }) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = user.photo),
                            contentDescription = "Photo de profil",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                        Text(
                            text = "${user.nom} ${user.prenom}",
                            modifier = Modifier.padding(start = 16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Column(
                            Modifier.fillMaxHeight()
                        ) {
                            Button(onClick = { onNavigate(Destination.FrigoUser(userId = user.id)) }) {
                                Text(text = "Voir Frigo")
                            }
                        }
                    }
                }
            }
        }
    }
}