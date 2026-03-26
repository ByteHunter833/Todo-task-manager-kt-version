package com.example.todotaskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: Int,
    val title : String,
    val isDone : Boolean
)