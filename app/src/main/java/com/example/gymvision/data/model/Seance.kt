package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seances")
data class Seance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val dureeMinutes: Int,
    val niveau: String,
    val isFavori: Boolean = false
)