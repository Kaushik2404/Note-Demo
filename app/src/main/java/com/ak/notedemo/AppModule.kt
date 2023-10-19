package com.ak.notedemo

import android.content.Context
import androidx.room.Room
import com.ak.notedemo.data.Note
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext app: Context
    )= Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        "NotesDatabse"
    ).build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.getNoteDao()

}