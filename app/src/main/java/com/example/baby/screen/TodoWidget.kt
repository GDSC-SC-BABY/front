/*
package com.example.baby.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baby.ui.theme.mainColor
import com.example.baby.ui.theme.todoListColor
import com.example.baby.viewModel.TodoViewModel


@Composable
fun TodayDateText(today: String) {
    Text(text = today, fontWeight = FontWeight.SemiBold, color = Color(0xff878787))
}

@Composable
fun WriteTodoContent(newTodo: String, onNewTodoChange: (String) -> Unit) {

    OutlinedTextField(
        value = newTodo,
        onValueChange = onNewTodoChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = mainColor,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        )
    )
}

@Composable
fun AddTodoButton(
    newTodo: String,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(2.dp, mainColor),
        enabled = newTodo.isNotBlank(),
        onClick = onClick,
        modifier = Modifier
            .height(40.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text("Add Todo")
    }
}

@Composable
fun WriteTodo(viewModel: TodoViewModel, navController: NavController) {
//    var newTodo by remember { mutableStateOf("") }

    var (newTodo, setNewTodo) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        WriteTodoContent(newTodo = newTodo, onNewTodoChange = setNewTodo)
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        AddTodoButton(newTodo = newTodo) {
            viewModel.insert(TodoEntity(title = newTodo, isCompleted = false))
            newTodo = ""
            navController.navigateUp()
        }
    }
}


@Composable
fun TodoItem(viewModel: TodoViewModel) {
    val todos by viewModel.todos2.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(todos) { todo ->
            Column() {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(todoListColor)
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = todo.isCompleted,
                            onCheckedChange = {
                                val updatedTodo = todo.copy(isCompleted = it)
                                viewModel.update(updatedTodo)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = todo.title,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xff656565),
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                viewModel.delete(todo)
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }

            }
        }
    }
}
*/
