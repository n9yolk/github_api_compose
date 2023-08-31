package com.example.githubapicompose.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubapicompose.data.RepoCard
import com.example.githubapicompose.data.RepoModel
import com.example.githubapicompose.data.TableData
import com.example.githubapicompose.data.UserInfo
import com.example.githubapicompose.data.UserModel
import com.example.githubapicompose.utils.MainViewModel
import org.json.JSONObject

@Composable
fun FavoritesScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory), onClick: (String) -> Unit
) {
    val itemsList = mainViewModel.itemsList.collectAsState(initial = emptyList())
    //Log.d("mylog", "user2: ${item}")
    //Log.d("mylog", "user2: ${list}")

    LazyColumn(
            modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            itemsList.value
        ) { _, item ->
            val repo = RepoModel(
                item.name,
                item.description,
                item.html_url,
                item.language
            )
            RepoCard(item = repo) {
                    htmlUrl -> onClick(htmlUrl)
            }
            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                Button(
                    onClick = {
                        mainViewModel.deleteItem(item)
                    }
                ) {
                    Text("Delete")
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

        }
    }
}