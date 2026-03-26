package com.example.todotaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todotaskmanager.database.TaskDatabase
import com.example.todotaskmanager.model.Task
import com.example.todotaskmanager.ui.theme.TodoTaskManagerTheme

class MainActivity : ComponentActivity() {

    private val database by lazy { TaskDatabase.getDatabase(this) }

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TodoTaskManagerTheme {
                val tasks by viewModel.tasks.collectAsState(initial = emptyList())
                var taskText by remember { mutableStateOf("") }
                var editingTaskId by remember { mutableStateOf<Int?>(null) }

                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.search_loop)
                )

                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                Scaffold(
                    containerColor = Color.White,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        TaskHeader(tasks)

                        Spacer(modifier = Modifier.height(16.dp))

                        TaskInput(
                            taskText = taskText,
                            onValueChange = { taskText = it }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (tasks.isEmpty()) {
                                item {
                                    EmptyState(
                                        composition = composition,
                                        progress = progress
                                    )
                                }
                            } else {
                                items(tasks) { task ->
                                    TaskItem(
                                        task = task,
                                        onToggle = {
                                            viewModel.toggleTask(task)
                                        },
                                        onEdit = {
                                            taskText = task.title
                                            editingTaskId = task.id
                                        },
                                        onDelete = {
                                            viewModel.deleteTask(task)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (taskText.isNotBlank()) {
                                    if (editingTaskId != null) {
                                        val oldTask = tasks.find { it.id == editingTaskId }

                                        if (oldTask != null) {
                                            val updatedTask = oldTask.copy(
                                                title = taskText.trim()
                                            )
                                            viewModel.editTask(updatedTask)
                                        }

                                        editingTaskId = null
                                    } else {
                                        viewModel.createTask(taskText)
                                    }

                                    taskText = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (editingTaskId != null) "Update Task"
                                else "Add Task"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskHeader(tasks: List<Task>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tasks",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Total: ${tasks.size}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TaskInput(taskText: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = taskText,
        onValueChange = onValueChange,
        label = { Text("Enter task") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    textDecoration = if (task.isDone) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
            )

            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggle() }
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Create, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun EmptyState(composition: LottieComposition?, progress: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
        Text("Add your first task")
    }
}