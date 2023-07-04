package com.notapp.domain.use_case

import com.notapp.domain.model.Note
import com.notapp.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNotes(private val notesRepository: NotesRepository) {
    operator fun invoke() : Observable<List<Note>> = notesRepository.getNotes()
}