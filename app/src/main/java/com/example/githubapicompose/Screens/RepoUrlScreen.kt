package com.example.githubapicompose.Screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.githubapicompose.utils.HtmlLoader


@Composable
fun RepoUrlScreen(htmlUrl: String) {

    HtmlLoader(htmlName = htmlUrl)
}