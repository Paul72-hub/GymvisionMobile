package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favoris_seances")
data class UserFavoriSeance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val seanceId: Int
)
