package com.ak.notedemo.presentation.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ak.notedemo.data.Note
import com.ak.notedemo.di.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModal  @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {



    fun update(note: Note){
        viewModelScope.launch {
            noteRepository.updateNotes(note)
        }
    }
}