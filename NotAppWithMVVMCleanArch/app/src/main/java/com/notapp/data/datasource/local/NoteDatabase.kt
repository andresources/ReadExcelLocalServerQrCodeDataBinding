package com.notapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.notapp.domain.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "note_db"
    }
}