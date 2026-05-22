package com.example.gymvision.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymvision.data.database.AppDatabase
import com.example.gymvision.data.model.Exercice
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.UserFavori
import com.example.gymvision.data.model.UserFavoriSeance
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class FavorisViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val exerciceDao = db.userFavoriDao()
    private val seanceDao = db.userFavoriSeanceDao()
    private val userId: String get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // ─── Exercices ───────────────────────────────────────────────────────────
    fun getFavorisExercices(): Flow<List<Exercice>> =
        if (userId.isEmpty()) emptyFlow() else exerciceDao.getFavorisExercices(userId)

    fun isFavoriExercice(exerciceId: Int): Flow<Boolean> =
        if (userId.isEmpty()) flowOf(false) else exerciceDao.isFavori(userId, exerciceId)

    fun toggleFavoriExercice(exerciceId: Int) = viewModelScope.launch {
        if (userId.isEmpty()) return@launch
        if (exerciceDao.isFavoriSync(userId, exerciceId)) {
            exerciceDao.removeFavori(userId, exerciceId)
        } else {
            exerciceDao.addFavori(UserFavori(userId = userId, exerciceId = exerciceId))
        }
    }

    // ─── Séances ─────────────────────────────────────────────────────────────
    fun getFavorisSeances(): Flow<List<Seance>> =
        if (userId.isEmpty()) emptyFlow() else seanceDao.getFavorisSeances(userId)

    fun isFavoriSeance(seanceId: Int): Flow<Boolean> =
        if (userId.isEmpty()) flowOf(false) else seanceDao.isFavori(userId, seanceId)

    fun toggleFavoriSeance(seanceId: Int) = viewModelScope.launch {
        if (userId.isEmpty()) return@launch
        if (seanceDao.isFavoriSync(userId, seanceId)) {
            seanceDao.removeFavori(userId, seanceId)
        } else {
            seanceDao.addFavori(UserFavoriSeance(userId = userId, seanceId = seanceId))
        }
    }
}
