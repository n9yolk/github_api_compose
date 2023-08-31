package com.example.githubapicompose.utils

sealed class DrawerEvents{
    data class OnItemClick(val title: String): DrawerEvents()
}
