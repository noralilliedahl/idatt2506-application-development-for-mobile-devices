package com.app.oving7.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    //returns -1 if a movie with the same title already exists
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie): Long

    @Query("SELECT * FROM movie ORDER BY title")
    suspend fun getAllMovies(): List<Movie>
}
