package com.notapp.data.repositry

import com.notapp.data.datasource.local.NoteDao
import com.notapp.domain.model.Note
import com.notapp.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NotesRepositoryImpl(private val noteDao: NoteDao) : NotesRepository {
    override fun getNotes(): Observable<List<Note>> =
        noteDao.getNotes()

    override fun insertNotes(note: Note): Completable =
        noteDao.insertNote(note)

    override fun deleteNote(note: Note): Completable =
        noteDao.deleteNote(note)
}