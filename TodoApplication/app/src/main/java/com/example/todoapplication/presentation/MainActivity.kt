package com.example.todoapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapplication.presentation.ui.theme.TodoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: NoteViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewModel.loadNoteInfo();




        setContent {
            TodoApplicationTheme {

                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable(route = "main") {
                        MainScreen(viewModel = viewModel, navController = navController)
                    }
                    composable(route = "add_edit") {
                        AddNote(viewModel = viewModel, navController = navController)
                    }
                }


            }
        }
    }
}


@Composable
fun AddNote(viewModel: NoteViewModel, navController: NavController) {

    var noteTitle by remember {
        mutableStateOf("")
    }

    var noteContent by remember {
        mutableStateOf("")
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.createNote(noteTitle, noteContent)
                navController.navigateUp()

            }) {
                Icon(Icons.Default.Check, contentDescription = null)
            }
        }
    ) {
        Column {
            TopAppBar {

                Row {
                    Button(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Text(text = "Add", style = TextStyle(fontSize = 30.sp))

                }
            }

            TextField(value = noteTitle, placeholder = {
                Text(text = "Note Title")
            }, onValueChange = {
                noteTitle = it
            })

            TextField(value = noteContent, placeholder = {
                Text(text = "Note Content")
            }, onValueChange = {
                noteContent = it
            })

        }


    }

}


@Composable
fun MainScreen(viewModel: NoteViewModel, navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_edit")
            }) {
                Icon(Icons.Default.Add, contentDescription = "null", tint = Color.White)
            }
        },
        backgroundColor = Color.Black
    ) {


        Column(
            modifier = Modifier.padding(start = 12.dp, top = 20.dp, end = 12.dp)
        ) {

            Text(
                text = "My Notes",
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.White
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.state.data?.size ?: 0) { noteIndex ->

                    val currentNote = viewModel.state.data?.get(noteIndex)

                    currentNote?.let {
                        Card(
                            backgroundColor = Color.White,
                            modifier = Modifier
//                                            .height(120.dp)
                                .fillMaxWidth()
                        ) {

                            Column {
                                Text(
                                    text = currentNote.title,
                                    modifier = Modifier.padding(start = 3.dp),
                                    style = TextStyle(
                                        fontSize = 23.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = currentNote.content,
                                    modifier = Modifier.padding(start = 3.dp)

                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }

//                                        Text(text = currentNote.content)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))


                }
            }
        }


    }
}