package com.example.gymvision.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymvision.data.database.AppDatabase
import com.example.gymvision.data.model.Exercice
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.SeanceExercice
import kotlinx.coroutines.launch

data class ExerciceSeanceItem(
    val exercice: Exercice,
    val repetitions: Int = 12,
    val series: Int = 3
)

class NouvelleSeanceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    var nom by mutableStateOf("")
        private set

    private val _exercices = mutableStateListOf<ExerciceSeanceItem>()
    val exercices: List<ExerciceSeanceItem> = _exercices

    val canSave: Boolean get() = nom.isNotBlank() && _exercices.isNotEmpty()

    fun updateNom(value: String) { nom = value }

    fun ajouterExercice(exerciceId: Int) {
        viewModelScope.launch {
            val exercice = db.exerciceDao().getById(exerciceId) ?: return@launch
            if (_exercices.none { it.exercice.id == exerciceId }) {
                _exercices.add(ExerciceSeanceItem(exercice))
            }
        }
    }

    fun retirerExercice(exerciceId: Int) {
        _exercices.removeAll { it.exercice.id == exerciceId }
    }

    fun sauvegarder(onSuccess: () -> Unit) {
        if (!canSave) return
        viewModelScope.launch {
            db.seanceDao().insert(
                Seance(
                    nom = nom.trim(),
                    dureeMinutes = _exercices.size * 8,
                    niveau = "Personnalisé"
                )
            ).also { seanceId ->
                _exercices.forEachIndexed { index, item ->
                    db.seanceDao().insertSeanceExercice(
                        SeanceExercice(
                            seanceId = seanceId.toInt(),
                            exerciceId = item.exercice.id,
                            repetitions = item.repetitions,
                            series = item.series,
                            ordre = index
                        )
                    )
                }
            }
            onSuccess()
        }
    }
}
