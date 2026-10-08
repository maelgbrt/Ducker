package com.example.ducker.ui.feature.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ducker.Destination

@Composable




fun UsersPage(
    modifier: Modifier = Modifier,
    onNavigate : (Destination) -> Unit) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {

        },

        bottomBar  = {
            Box(
                modifier = modifier.fillMaxWidth()
                    .background(Color.Blue)
                    .padding( 0.dp,16.dp)
                    ,
                        contentAlignment = Alignment.Center,


            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(17.dp),


                    modifier = modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.Green)
                        .padding(10.dp, 5.dp),
                ) {
                    Button(onClick = { }) {
                        Text(text = "My Fridge")
                    }
                    Button(onClick = { }) {
                        Text(text = "Explore")
                    }
                    Button(onClick = { }) {
                        Text(text = "Matchs")
                    }
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                    "salukekk"
            )

        }
    }

}
