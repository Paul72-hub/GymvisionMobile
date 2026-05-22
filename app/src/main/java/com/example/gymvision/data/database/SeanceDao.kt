package com.example.gymvision.data.database

import androidx.room.*
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.SeanceExercice
import kotlinx.coroutines.flow.Flow

@Dao
interface SeanceDao {

    @Query("SELECT * FROM seances")
    fun getAll(): Flow<List<Seance>>

    @Query("SELECT * FROM seances WHERE id = :id")
    suspend fun getById(id: Int): Seance?

    @Query("SELECT * FROM seances WHERE isFavori = 1")
    fun getFavoris(): Flow<List<Seance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seance: Seance): Long

    @Update
    suspend fun update(seance: Seance)

    @Delete
    suspend fun delete(seance: Seance)

    // Exercices d'une séance
    @Query("SELECT * FROM seance_exercices WHERE seanceId = :seanceId ORDER BY ordre")
    fun getExercicesDeSeance(seanceId: Int): Flow<List<SeanceExercice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeanceExercice(seanceExercice: SeanceExercice)

    @Delete
    suspend fun deleteSeanceExercice(seanceExercice: SeanceExercice)

    @Query("SELECT COUNT(*) FROM seances")
    suspend fun count(): Int
}