package com.example.githubapicompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name_table")
data class TableData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var html_url: String,
    var description: String,
    var language: String
)