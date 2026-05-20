package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercices")
data class Exercice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val description: String,
    val muscle: String,        // ex: Dos, Pectoraux
    val niveau: String,        // ex: Débutant, Intermédiaire, Confirmé
    val conseils: String,      // conseils de posture séparés par ;
    val isFavori: Boolean = false
)