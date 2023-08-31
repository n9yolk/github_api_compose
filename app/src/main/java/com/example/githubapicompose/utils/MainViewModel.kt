package com.example.githubapicompose.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.githubapicompose.data.MainDb
import com.example.githubapicompose.data.RepoModel
import com.example.githubapicompose.data.TableData
import kotlinx.coroutines.launch

class MainViewModel(val database: MainDb): ViewModel() {

    val itemsList = database.dao.getAllItems()

    fun insertItem(item: TableData) = viewModelScope.launch {
        database.dao.insertItem(item)
    }

    fun deleteItem(item: TableData) = viewModelScope.launch {
        database.dao.deleteItem(item)
    }
    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}