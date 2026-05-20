package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Lien entre une séance et ses exercices (avec reps et séries)
@Entity(tableName = "seance_exercices")
data class SeanceExercice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val seanceId: Int,
    val exerciceId: Int,
    val repetitions: Int,
    val series: Int,
    val ordre: Int
)