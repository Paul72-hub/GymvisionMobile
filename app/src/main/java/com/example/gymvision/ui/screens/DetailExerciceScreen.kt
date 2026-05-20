package com.example.gymvision.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailExerciceScreen(
    exerciceId: Int,
    onRetour: () -> Unit,
    onCommencer: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Détail exercice $exerciceId — à construire")
    }
}