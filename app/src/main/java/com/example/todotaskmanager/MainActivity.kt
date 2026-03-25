package com.example.todotaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.unit.dp
import com.example.todotaskmanager.ui.theme.TodoTaskManagerTheme


data class Task(
    val title: String,
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
                    var taskText by remember { mutableStateOf("")}
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
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tasks) { task ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = task.title,
                                        modifier = Modifier.weight(1f),
                                        style = TextStyle(
                                            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                                        )
                                    )
                                    Checkbox(
                                        checked = task.isDone,
                                        onCheckedChange = {
                                            tasks = tasks.map {
                                                if (it == task) it.copy(isDone = !it.isDone) else it
                                            }
                                        }
                                    )

                                }
                            }
                        }
                        Button(onClick = {
                            if(taskText.isNotBlank()){
                                val task = Task(title = taskText, isDone = false)
                                tasks+= task
                                taskText = ""


                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Add Task")
                        }
                    }
                }
            }
        }
    }
}


