package com.example.gymvision.data.database

import androidx.room.*
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.UserFavoriSeance
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriSeanceDao {

    @Query("""
        SELECT s.* FROM seances s
        INNER JOIN user_favoris_seances ufs ON s.id = ufs.seanceId
        WHERE ufs.userId = :userId
    """)
    fun getFavorisSeances(userId: String): Flow<List<Seance>>

    @Query("SELECT COUNT(*) > 0 FROM user_favoris_seances WHERE userId = :userId AND seanceId = :seanceId")
    fun isFavori(userId: String, seanceId: Int): Flow<Boolean>

    @Query("SELECT COUNT(*) > 0 FROM user_favoris_seances WHERE userId = :userId AND seanceId = :seanceId")
    suspend fun isFavoriSync(userId: String, seanceId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavori(favori: UserFavoriSeance)

    @Query("DELETE FROM user_favoris_seances WHERE userId = :userId AND seanceId = :seanceId")
    suspend fun removeFavori(userId: String, seanceId: Int)
}
