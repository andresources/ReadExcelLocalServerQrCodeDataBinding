package com.notapp.domain.use_case

import com.notapp.domain.model.Note
import com.notapp.domain.repository.NotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DeleteNote(private val notesRepository: NotesRepository) {
    operator fun invoke(note: Note) : Completable = notesRepository.deleteNote(note)
}