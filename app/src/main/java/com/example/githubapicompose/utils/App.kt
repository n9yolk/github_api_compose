package com.example.githubapicompose.utils

import android.app.Application
import com.example.githubapicompose.data.MainDb

class App: Application() {
    val database by lazy {MainDb.createDataBase(this)}

}