package com.ak.notedemo.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ak.notedemo.data.Note
import com.ak.notedemo.di.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModal @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var allNotes = MutableLiveData<List<Note>>()

    fun fetchData(type:String){
        viewModelScope.launch {
            allNotes = noteRepository.allNotes(type) as MutableLiveData<List<Note>>
        }
    }


    fun delete(id:Int){
        viewModelScope.launch {
            noteRepository.deleteNotes(id)
        }
    }
    fun update(note: Note){
        viewModelScope.launch {
            noteRepository.updateNotes(note)
        }
    }

}