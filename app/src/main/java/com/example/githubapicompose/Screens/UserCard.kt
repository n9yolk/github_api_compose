package com.example.githubapicompose.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun UserCard(item: UserModel, onClick: (UserModel) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick(item)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = "im5",
                    modifier = Modifier
                        .padding(
                            end = 6.dp
                        )
                        .size(50.dp)
                )
                Text(text = item.login)
            }
        }
    }
}