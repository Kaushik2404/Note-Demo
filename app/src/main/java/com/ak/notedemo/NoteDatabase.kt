package com.ak.notedemo

import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ak.notedemo.data.Note
import com.ak.notedemo.di.NoteDao

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase  :RoomDatabase(){
    abstract  fun getNoteDao() : NoteDao

}