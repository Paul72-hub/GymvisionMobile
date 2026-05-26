package com.example.gymvision.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gymvision.data.database.AppDatabase
import com.example.gymvision.data.model.Exercice
import kotlinx.coroutines.flow.Flow

class RechercherViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val exercices: Flow<List<Exercice>> = db.exerciceDao().getAll()
}
