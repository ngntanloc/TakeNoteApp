package com.example.todoapp.data

import androidx.compose.runtime.MutableState
import androidx.room.*
import com.example.todoapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * FROM notes_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_tbl WHERE id = :id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun remove(note: Note)

    @Query("DELETE FROM notes_tbl")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

}
