package com.example.gymvision.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymvision.data.database.AppDatabase
import com.example.gymvision.data.model.Seance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExerciceInfo(val exerciceId: Int, val nom: String, val ordre: Int)

class DetailSeanceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    private val _seance = MutableStateFlow<Seance?>(null)
    val seance: StateFlow<Seance?> = _seance

    private val _exercices = MutableStateFlow<List<ExerciceInfo>>(emptyList())
    val exercices: StateFlow<List<ExerciceInfo>> = _exercices

    val exerciceIds: List<Int> get() = _exercices.value.map { it.exerciceId }

    fun load(seanceId: Int) {
        viewModelScope.launch {
            _seance.value = db.seanceDao().getById(seanceId)
            db.seanceDao().getExercicesDeSeance(seanceId).collect { seanceExercices ->
                _exercices.value = seanceExercices.map { se ->
                    val nom = db.exerciceDao().getById(se.exerciceId)?.nom ?: "Exercice inconnu"
                    ExerciceInfo(exerciceId = se.exerciceId, nom = nom, ordre = se.ordre)
                }
            }
        }
    }
}
