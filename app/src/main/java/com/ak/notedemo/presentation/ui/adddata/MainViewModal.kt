package com.ak.notedemo.presentation.ui.adddata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ak.notedemo.data.Note
import com.ak.notedemo.di.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModal @Inject constructor (
    private val noteRepository: NoteRepository
) :ViewModel() {

//    suspend fun allNotes(): LiveData<List<Note>> {
//        return noteRepository.allNotes()
//    }

    fun insert(note: Note){
        viewModelScope.launch {
            noteRepository.insertNotes(note)

        }

    }

}