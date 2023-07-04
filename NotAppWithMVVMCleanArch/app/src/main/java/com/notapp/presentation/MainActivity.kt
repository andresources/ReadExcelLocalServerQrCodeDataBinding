package com.notapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.notapp.R
import com.notapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupAdapter()
        observeNoteState()
    }

    private fun observeNoteState() {
        viewModel.notesState.observe(this) {
            when(it) {
                is NotesState.NotesFetched -> {
                    noteAdapter.updateList(it.notes ?: listOf())
                }
                is NotesState.NotesInserted -> {
                    Snackbar
                        .make(binding.root, "Note Successfully Inserted", Snackbar.LENGTH_LONG).show()
                }
                is NotesState.NotesDeleted -> {
                    Snackbar
                        .make(binding.root, "Note Deleted", Snackbar.LENGTH_LONG).show()
                }
                is NotesState.Error -> {
                    Snackbar
                        .make(binding.root, it.message ?: "Error Occured", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            noteAdapter = NoteAdapter { note -> viewModel.deleteNotes(note) }
            adapter = noteAdapter
        }
    }
}