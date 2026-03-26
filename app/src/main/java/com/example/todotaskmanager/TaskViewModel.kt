package com.example.todotaskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotaskmanager.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskDao: TaskDao
) : ViewModel() {

    val tasks : Flow<List<Task>> = taskDao.getAllTasks()

    fun createTask(title : String){
        viewModelScope.launch {
            val newTask = Task(
                title = title.trim(),
                isDone = false
            )
            taskDao.createTask(newTask)

        }
    }
    fun editTask(task: Task){
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }
    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)

        }
    }
    fun toggleTask(task: Task){
        viewModelScope.launch {
            val updatedTask = task.copy(
                isDone = !task.isDone
            )
            taskDao.updateTask(updatedTask)
        }
    }
    }

