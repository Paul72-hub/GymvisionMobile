package com.example.gymvision.data.database

import androidx.room.*
import com.example.gymvision.data.model.Exercice
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciceDao {

    @Query("SELECT * FROM exercices")
    fun getAll(): Flow<List<Exercice>>

    @Query("SELECT * FROM exercices WHERE id = :id")
    suspend fun getById(id: Int): Exercice?

    @Query("SELECT * FROM exercices WHERE nom LIKE '%' || :recherche || '%'")
    fun rechercher(recherche: String): Flow<List<Exercice>>

    @Query("SELECT * FROM exercices WHERE muscle = :muscle")
    fun getByMuscle(muscle: String): Flow<List<Exercice>>

    @Query("SELECT * FROM exercices WHERE niveau = :niveau")
    fun getByNiveau(niveau: String): Flow<List<Exercice>>

    @Query("SELECT * FROM exercices WHERE isFavori = 1")
    fun getFavoris(): Flow<List<Exercice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercice: Exercice): Long

    @Update
    suspend fun update(exercice: Exercice)

    @Delete
    suspend fun delete(exercice: Exercice)

    @Query("SELECT COUNT(*) FROM exercices")
    suspend fun count(): Int
}