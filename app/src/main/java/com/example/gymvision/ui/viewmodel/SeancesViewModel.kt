package com.example.gymvision.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gymvision.data.database.AppDatabase
import com.example.gymvision.data.model.Seance
import kotlinx.coroutines.flow.Flow

class SeancesViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val seances: Flow<List<Seance>> = db.seanceDao().getAll()
}
