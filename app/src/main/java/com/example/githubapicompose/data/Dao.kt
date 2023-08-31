package com.example.githubapicompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(nameEntity: TableData)
    @Delete
    suspend fun deleteItem(nameEntity: TableData)
    @Query("SELECT * FROM name_table")
    fun getAllItems(): Flow<List<TableData>>
}