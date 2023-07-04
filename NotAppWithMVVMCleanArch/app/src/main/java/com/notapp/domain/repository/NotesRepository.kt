package com.notapp.domain.repository

import com.notapp.domain.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface NotesRepository {
    fun getNotes() : Observable<List<Note>>

    fun insertNotes(note: Note) : Completable

    fun deleteNote(note: Note) : Completable
}