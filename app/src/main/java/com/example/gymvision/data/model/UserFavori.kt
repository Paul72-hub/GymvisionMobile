package com.example.gymvision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favoris")
data class UserFavori(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val exerciceId: Int
)
