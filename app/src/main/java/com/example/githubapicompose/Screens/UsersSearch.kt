package com.example.githubapicompose.Screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.githubapicompose.data.UserCard
import com.example.githubapicompose.data.RepoModel
import com.example.githubapicompose.data.UserInfo
import com.example.githubapicompose.data.UserModel
import com.example.githubapicompose.utils.ApiRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject


@Composable
fun UsersSearch(context: Context, topBarTitle: MutableState<String>, onClick: (UserModel) -> Unit) {
    val dialogText = remember {
        mutableStateOf("")
    }
    val usersList = remember {
        mutableStateOf(listOf<UserModel>())
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = dialogText.value,
            onValueChange = {
                dialogText.value = it
            },
            //shape = RectangleShape,
            label = { Text("username") },
            modifier = Modifier.padding(
                top = 15.dp,
                bottom = 5.dp
            )
        )

        Button(onClick = {
            if (dialogText.value == ""){
                scope.launch {
                    snackbarHostState.value.showSnackbar("Fill in the search field!")
                }
            }
            else
                getUsers(dialogText.value, context, usersList)
        }) {
            Text("Search")
        }

        SnackbarHost(snackbarHostState.value)

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                usersList.value
            ) { _, item ->
                UserCard(item = item){ listUser ->
                    topBarTitle.value = "User: " + listUser.login
                    onClick(item)
                }
            }
        }
    }
}

private fun parseUserData(result: JSONObject): List<UserModel> {
    val list = parseUsers(result)
    Log.d("mylog", "login: ${list[0].login}")
    Log.d("mylog", "imageUrl: ${list[0].imageUrl}")
    return list
}

private fun parseUsers(mainObject: JSONObject): List<UserModel> {
    val list = ArrayList<UserModel>()
    val userArray = mainObject.getJSONArray("items")
    for (i in 0 until userArray.length()) {
        val user = userArray[i] as JSONObject
        val item = UserModel(
            user.getString("login"),
            user.getString("avatar_url")
        )
        list.add(item)
    }
    return list
}

private fun getUsers(name: String, context: Context, userList: MutableState<List<UserModel>>) {
    CoroutineScope(Dispatchers.IO).launch {
        val url = "https://api.github.com/search/users?q=$name"
        val list = parseUserData(ApiRequest(context).apiRequestObject(url))
        userList.value = list
    }
}