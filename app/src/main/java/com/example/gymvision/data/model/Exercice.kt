package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercices")
data class Exercice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val description: String,
    val muscle: String,
    val niveau: String,
    val conseils: String,      // conseils de posture séparés par ;
    val youtubeVideoId: String = "",
    val isFavori: Boolean = false
)