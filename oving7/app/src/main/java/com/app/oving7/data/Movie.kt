package com.app.oving7.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//unique title so the same movie is only inserted once, even if the file is read on every startup
@Entity(tableName = "movie", indices = [Index(value = ["title"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val released: Int
)
