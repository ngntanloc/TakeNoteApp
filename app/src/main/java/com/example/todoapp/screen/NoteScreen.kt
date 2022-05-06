package com.example.todoapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.components.NoteButton
import com.example.todoapp.components.NoteInputText
import com.example.todoapp.data.NotesDateSource
import com.example.todoapp.model.Note
import com.example.todoapp.util.formatDate
import java.time.format.DateTimeFormatter

@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {

    var title = remember {
        mutableStateOf("")
    }

    var description = remember {
        mutableStateOf("")
    }

    var context = LocalContext.current

    val enabled = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                Icon(imageVector = Icons.Rounded.List, contentDescription = "Icon")
            },
            backgroundColor = Color(0xFFDADFE3)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(text = title.value, label = "Tittle", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) title.value = it
            })
            NoteInputText(text = description.value, label = "Add a note", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) description.value = it
            })
            NoteButton(text = "Add", onClick = {
                if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))
                    title.value = ""
                    description.value = ""
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                }
            })
        }

        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes) { note ->
                NoteRow(note = note, onNoteClicked = {
                    onRemoveNote(it)
                })
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        elevation = 6.dp
    ) {
        Column(
            modifier
                .clickable {
                    onNoteClicked(note)
                }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle2)
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = NotesDateSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}