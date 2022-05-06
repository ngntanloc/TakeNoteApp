package com.example.todoapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.model.Note


@Composable
fun TodoDialog(
    modifier: Modifier = Modifier.padding(all = 8.dp),
    title: String = "",
    todo: String = "",
    onUpdateClicked: () -> Unit,
    onFinishClicked: () -> Unit
) {
    val openDialog = remember {
        mutableStateOf(true)
    }
    val newTitle = remember {
        mutableStateOf(title)
    }
    val newTodoThings = remember {
        mutableStateOf(todo)
    }

    if (openDialog.value) {
        AlertDialog (
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Column() {
                    NoteInputText(text = newTitle.value, label = "Tittle", onTextChange = {
                        if (it.all { char ->
                                char.isLetter() || char.isWhitespace()
                            }) newTitle.value = it
                    })
                    NoteInputText(text = newTitle.value, label = "Todo things", onTextChange = {
                        if (it.all { char ->
                                char.isLetter() || char.isWhitespace()
                            }) newTodoThings.value = it
                    })
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier,
                        onClick = onUpdateClicked
                    ) {
                        Text("Update")
                    }
                    Button(
                        modifier = Modifier,
                        onClick = onFinishClicked
                    ) {
                        Text("Done")
                    }
                }
            }
        )
    }
}

