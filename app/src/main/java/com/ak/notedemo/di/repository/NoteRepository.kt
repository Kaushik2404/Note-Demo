package com.ak.notedemo.di.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ak.notedemo.data.Note
import com.ak.notedemo.di.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertNotes(note: Note) =noteDao.insertNote(note =note)
    suspend fun updateNotes(note: Note) =noteDao.updateNote(note =note)
    suspend fun allNotes(type:String) = liveData {
        emit(noteDao.getAllNotes(type))
    }
    suspend fun deleteNotes(id:Int) =noteDao.deleteNote(id)
}