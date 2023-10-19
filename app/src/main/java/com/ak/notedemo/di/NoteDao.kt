package com.ak.notedemo.di

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ak.notedemo.data.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertNote( note: Note)

    @Query("DELETE FROM Notes WHERE noteId = :id")
    suspend fun deleteNote( id: Int)

    @Update
    suspend fun updateNote( note: Note)

    @Query("Select * from Notes Where `view` = :type")
    suspend fun getAllNotes(type:String ):List<Note>

}