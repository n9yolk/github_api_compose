package com.example.githubapicompose.Screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
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
import androidx.compose.ui.unit.sp
import com.example.githubapicompose.data.RepoCard
import com.example.githubapicompose.data.RepoModel
import com.example.githubapicompose.data.UserModel
import com.example.githubapicompose.ui_components.DropDownMenu
import com.example.githubapicompose.utils.ApiRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import org.json.JSONArray
import org.json.JSONObject


@Composable
fun RepoSearch(context: Context, topBarTitle: MutableState<String>, onClick: (String) -> Unit) {
    val languages = listOf<String>("Any", "TypeScript", "JavaScript", "CSS", "Rust", "HTML",
        "Shell", "PowerShell", "Cuda", "C++",
        "Python", "Perl", "Ruby", "TeX", "Objective-C", "Objective-C++",
        "Clojure", "PHP", "Julia", "Jupyter Notebook",
        "Visual Basic .NET", "Dockerfile", "C", "C#", "Pug", "Go", "F#", "Java",
        "CoffeeScript", "R", "Dart", "Swift", "Lua")

    val sortBy = listOf<String>("Default", "stars", "forks", "help-wanted-issues", "updated")

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }

    var lFromLanguages: String = "Any"
    var tFromSortby: String = "Default"

    val dialogText = remember {
        mutableStateOf("")
    }
    val reposList = remember {
        mutableStateOf(listOf<RepoModel>())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ){
            TextField(
                value = dialogText.value,
                onValueChange = {
                    dialogText.value = it
                },
                //shape = RectangleShape,
                label = { Text("name of repository") },
                modifier = Modifier.padding(
                    top = 15.dp,
                    bottom = 5.dp,
                    end = 5.dp
                )
            )

            Button(
                modifier = Modifier.padding(
                    top = 15.dp,
                    bottom = 5.dp
                ),
                onClick = {
                    if (dialogText.value == ""){
                        scope.launch {
                            snackbarHostState.value.showSnackbar("Fill in the search field!")
                        }
                    }
                    else
                        getRepos(dialogText.value, lFromLanguages, tFromSortby, context, reposList)
                }) {
                Text("Search")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ){

            DropDownMenu(suggestions = languages, label = "Language", 0.5f){
                label-> lFromLanguages = label
            }
            DropDownMenu(suggestions = sortBy, label = "Sort by", 1f){
                label-> tFromSortby = label
            }
        }
        SnackbarHost(snackbarHostState.value)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                reposList.value
            ) { _, item ->
                RepoCard(item = item){ htmlUrl ->
                    onClick(htmlUrl)
                }
            }
        }
    }
}

private fun parseReposData(result: JSONObject): ArrayList<RepoModel> {
    val reslist: ArrayList<RepoModel> = ArrayList<RepoModel>()
    val repoArray = result.getJSONArray("items")
    for (i in 0 until repoArray.length()) {
        val repo = repoArray[i] as JSONObject
        val item = RepoModel(
            repo.getString("name"),
            repo.getString("html_url"),
            repo.getString("description"),
            repo.getString("language")
        )
        //Log.d("mylog", "item: ${item}")
        reslist.add(item)
    }
    return reslist
}

private fun getRepos(name:String, language: String, sortType: String, context: Context, repoList: MutableState<List<RepoModel>>) {
    CoroutineScope(Dispatchers.IO).launch {
        var url = ""
        if(language == "Any"){
            if(sortType == "Default"){
                url = "https://api.github.com/search/repositories?q=$name"
            }
            else{
                url = "https://api.github.com/search/repositories?q=$name+sort=$sortType"
            }
        }
        else{
            if(sortType == "Default"){
                url = "https://api.github.com/search/repositories?q=$name+language:$language"
            }
            else{
                url = "https://api.github.com/search/repositories?q=$name+language:$language&sort=$sortType"
            }
        }
        Log.d("mylog", "name: ${name}")
        Log.d("mylog", "lang: ${language}")
        Log.d("mylog", "sort: ${sortType}")
        Log.d("mylog", "url: ${url}")
        val list = parseReposData(ApiRequest(context).apiRequestObject(url))
        Log.d("mylog", "item: ${list}")
        repoList.value = list
    }
}