package com.example.gymvision.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AccueilScreen(
    onNavigateToSeances: () -> Unit,
    onNavigateToRechercher: () -> Unit,
    onNavigateToFavoris: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Accueil — à construire")
    }
}