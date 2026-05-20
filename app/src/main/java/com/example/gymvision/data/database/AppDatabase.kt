package com.example.gymvision.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymvision.data.model.Exercice
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.SeanceExercice

@Database(
    entities = [Exercice::class, Seance::class, SeanceExercice::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciceDao(): ExerciceDao
    abstract fun seanceDao(): SeanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymvision_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}