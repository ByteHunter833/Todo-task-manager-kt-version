package com.example.todotaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todotaskmanager.ui.theme.TodoTaskManagerTheme
import kotlin.random.Random

data class Task(
    val title: String,
    val id: Int,
    val isDone: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var tasks by remember { mutableStateOf(listOf<Task>()) }
                    var taskText by remember { mutableStateOf("") }
                    var editingTaskId by remember { mutableStateOf<Int?>(null) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Tasks",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = taskText,
                            onValueChange = { taskText = it },
                            label = { Text("Enter task") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tasks) { task ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(

                                        modifier = Modifier.fillMaxWidth().padding(
                                            horizontal = 12.dp
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = task.title,
                                            modifier = Modifier.weight(1f),
                                            style = TextStyle(
                                                textDecoration = if (task.isDone)
                                                    TextDecoration.LineThrough
                                                else
                                                    TextDecoration.None
                                            )
                                        )

                                        Checkbox(
                                            checked = task.isDone,
                                            onCheckedChange = {
                                                tasks = tasks.map {
                                                    if (it == task) {
                                                        it.copy(isDone = !it.isDone)
                                                    } else {
                                                        it
                                                    }
                                                }
                                            }
                                        )

                                        IconButton(
                                            onClick = {
                                                tasks = tasks.filter { it != task }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        //Edit button
                                        IconButton(
                                            onClick = {
                                                taskText = task.title
                                                editingTaskId = task.id
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Create,
                                                contentDescription = "Edit",
                                                tint = MaterialTheme.colorScheme.surfaceTint
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if(editingTaskId!=null){
                                    tasks = tasks.map {
                                        if (it.id == editingTaskId) {
                                            it.copy(title = taskText.trim())

                                        } else {
                                            it
                                        }
                                    }
                                    taskText = ""
                                }else{
                                    val task = Task(title = taskText, isDone = false, id = randomId())
                                    tasks += task
                                    taskText = ""
                                    editingTaskId = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if(editingTaskId !=null) "Update Task" else "Add Task")
                        }
                    }
                }
            }
        }
    }
}
fun randomId() : Int{
    return Random.nextInt()
}