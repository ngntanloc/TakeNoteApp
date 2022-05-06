package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.NotesDateSource
import com.example.todoapp.model.Note
import com.example.todoapp.screen.NoteScreen
import com.example.todoapp.screen.NoteViewModel
import com.example.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
//                    val noteViewModel = viewModel<NoteViewModel>() also work
                    val noteViewModel: NoteViewModel by viewModels()
                    NotesApp(noteViewModel = noteViewModel)
                }
            }
        }
    }
}

@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val noteList = noteViewModel.noteList.collectAsState().value

    NoteScreen(notes = noteList, onAddNote = {
        noteViewModel.addNote(it)
    }, onRemoveNote = {
        noteViewModel.removeNote(it)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoAppTheme {
        NoteScreen(notes = emptyList(), onAddNote = {}, onRemoveNote = {})
    }
}