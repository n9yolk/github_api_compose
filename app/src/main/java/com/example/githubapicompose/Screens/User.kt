package com.example.githubapicompose.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubapicompose.data.RepoCard
import com.example.githubapicompose.data.UserCard
import com.example.githubapicompose.data.UserInfo
import com.example.githubapicompose.data.UserModel

@Composable
fun User(item: UserInfo, onClick: (String) -> Unit) {
    //Log.d("mylog", "user2: ${item}")
    val list = item.repositories.toList()
    //Log.d("mylog", "user2: ${list}")
    Column(
        modifier = Modifier.fillMaxSize(),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = item.login)
                        Text(text = item.followers)
                    }

                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                list
            ) { _, item ->
                RepoCard(item = item) {
                    htmlUrl -> onClick(htmlUrl)
                }
            }
        }
    }

}