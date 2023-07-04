package com.notapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notapp.domain.model.Note
import com.notapp.domain.use_case.DeleteNote
import com.notapp.domain.use_case.GetNotes
import com.notapp.domain.use_case.InsertNote

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val insertNote: InsertNote,
    private val deleteNote: DeleteNote
) : ViewModel() {

    private val _notesState = MutableLiveData<NotesState>()
    val notesState: LiveData<NotesState> get() = _notesState

    val noteTitle = MutableLiveData<String>("")
    val noteDesc = MutableLiveData<String>("")

    private val disposables = CompositeDisposable()

    init {
        fetchNotes()
    }

    fun fetchNotes() {
         disposables.add(getNotes()
             .subscribeOn(Schedulers.io())
             .map { it.sortedByDescending { note -> note.id } }
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(
                {
                    _notesState.value = NotesState.NotesFetched(it)
                },
                {
                    _notesState.value = NotesState.Error(it.localizedMessage)
                }
            ))
    }

    fun insertNotes() {
        val note = Note(title = noteTitle.value!!, content = noteDesc.value!!)
        disposables.add(insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _notesState.postValue(NotesState.NotesInserted)
                },
                {
                    _notesState.postValue(NotesState.Error(it.localizedMessage))
                }
            ))
    }

    fun deleteNotes(note: Note) {
        disposables.add(deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _notesState.value = NotesState.NotesDeleted
                },
                {
                    _notesState.value = NotesState.Error(it.localizedMessage)
                }
            ))
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}