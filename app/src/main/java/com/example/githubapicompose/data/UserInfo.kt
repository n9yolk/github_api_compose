package com.example.githubapicompose.data

data class UserInfo(
    var login: String,
    var imageUrl: String,
    var followers: String,
    var repositories: ArrayList<RepoModel>
)