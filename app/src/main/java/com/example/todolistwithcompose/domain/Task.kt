package com.example.todolistwithcompose.domain

import java.util.Date


data class Task(
    val id:Long = 0,
    var title: String,
    var content: String,
    var date: Date,
    val taskGroup: TaskGroup,
    var status: TaskStatus,
)