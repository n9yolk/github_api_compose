package com.example.githubapicompose

import android.annotation.SuppressLint
import android.app.LauncherActivity.ListItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubapicompose.Screens.FavoritesScreen
import com.example.githubapicompose.Screens.MainScreen
import com.example.githubapicompose.Screens.RepoSearch
import com.example.githubapicompose.Screens.RepoUrlScreen
import com.example.githubapicompose.Screens.User
import com.example.githubapicompose.Screens.UsersSearch
import com.example.githubapicompose.data.RepoModel
import com.example.githubapicompose.data.UserInfo
import com.example.githubapicompose.ui.theme.GitHubAPIComposeTheme
import com.example.githubapicompose.ui_components.DrawerMenu
import com.example.githubapicompose.ui_components.MainTopBar
import com.example.githubapicompose.utils.DrawerEvents
import com.example.githubapicompose.utils.GetUserInfo
import com.example.githubapicompose.utils.Routes
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            var repoUrlStr = remember {
                mutableStateOf("")
            }
            val topBarTitle = remember {
                mutableStateOf("Menu")
            }
            var itemUser = remember {
                mutableStateOf(UserInfo("","","", ArrayList<RepoModel>()))
            }

            GitHubAPIComposeTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        MainTopBar(title = topBarTitle.value, scaffoldState = scaffoldState) {
                            topBarTitle.value = "Favorites"
                            navController.navigate(Routes.FAVE_SCREEN)
                        }
                    },
                    drawerContent = {
                        DrawerMenu() { event ->
                            when (event) {
                                is DrawerEvents.OnItemClick -> {
                                    topBarTitle.value = event.title
                                    navController.navigate(event.title)
                                }
                            }
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController, startDestination = Routes.MAIN_SCREEN
                    ){
                        composable(Routes.MAIN_SCREEN){
                            MainScreen(navController = navController, topBarTitle = topBarTitle)
                        }
                        composable(Routes.USERS_SEARCH_SCREEN){
                            UsersSearch(context = this@MainActivity, topBarTitle = topBarTitle){
                                userModel ->
                                coroutineScope.launch {
                                    itemUser.value.login = userModel.login
                                    itemUser.value.imageUrl = userModel.imageUrl
                                    itemUser.value.followers = async {
                                        GetUserInfo().getUserFollowers(userModel.login, this@MainActivity)
                                    }.await()
                                    itemUser.value.repositories = async {
                                        GetUserInfo().getUserRepos(userModel.login, this@MainActivity)
                                    }.await()
                                    navController.navigate(Routes.User_SCREEN)
                                }
                            }
                        }
                        composable(Routes.REPO_SEARCH_SCREEN){
                            RepoSearch(context = this@MainActivity, topBarTitle = topBarTitle){
                                htmlUrl -> repoUrlStr.value = htmlUrl
                                navController.navigate(Routes.REPO_URl_SCREEN)
                            }
                        }
                        composable(Routes.REPO_URl_SCREEN){
                            RepoUrlScreen(repoUrlStr.value)
                        }
                        composable(Routes.User_SCREEN){
                            User(itemUser.value){
                                htmlUrl -> repoUrlStr.value = htmlUrl
                                navController.navigate(Routes.REPO_URl_SCREEN)
                            }
                        }
                        composable(Routes.FAVE_SCREEN){
                            FavoritesScreen(){
                                htmlUrl -> repoUrlStr.value = htmlUrl
                                navController.navigate(Routes.REPO_URl_SCREEN)
                            }
                        }
                    }
                }
            }
        }
    }
}
