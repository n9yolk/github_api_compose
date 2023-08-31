package com.example.githubapicompose.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.githubapicompose.utils.Routes

@Composable
fun MainScreen(navController: NavHostController, topBarTitle: MutableState<String>) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            topBarTitle.value = "Users Search"
            navController.navigate(Routes.USERS_SEARCH_SCREEN)
        }) {
            Text("Users Search")
        }

        Button(onClick = {
            topBarTitle.value = "Repo Search"
            navController.navigate(Routes.REPO_SEARCH_SCREEN)
        }) {
            Text("Repo Search")
        }
    }
}