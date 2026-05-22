package com.example.gymvision.data.database

import androidx.room.*
import com.example.gymvision.data.model.Exercice
import com.example.gymvision.data.model.UserFavori
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriDao {

    @Query("""
        SELECT e.* FROM exercices e
        INNER JOIN user_favoris uf ON e.id = uf.exerciceId
        WHERE uf.userId = :userId
    """)
    fun getFavorisExercices(userId: String): Flow<List<Exercice>>

    @Query("SELECT COUNT(*) > 0 FROM user_favoris WHERE userId = :userId AND exerciceId = :exerciceId")
    fun isFavori(userId: String, exerciceId: Int): Flow<Boolean>

    @Query("SELECT COUNT(*) > 0 FROM user_favoris WHERE userId = :userId AND exerciceId = :exerciceId")
    suspend fun isFavoriSync(userId: String, exerciceId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavori(favori: UserFavori)

    @Query("DELETE FROM user_favoris WHERE userId = :userId AND exerciceId = :exerciceId")
    suspend fun removeFavori(userId: String, exerciceId: Int)
}
