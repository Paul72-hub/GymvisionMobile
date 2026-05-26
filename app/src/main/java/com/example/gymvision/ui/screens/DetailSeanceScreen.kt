package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.example.gymvision.ui.viewmodel.DetailSeanceViewModel
import com.example.gymvision.ui.viewmodel.FavorisViewModel

@Composable
fun DetailSeanceScreen(
    seanceId: Int,
    onRetour: () -> Unit,
    onNavigateToDetailExercice: (Int) -> Unit,
    onCommencerSeance: (Int) -> Unit = {},
    favorisViewModel: FavorisViewModel = viewModel(),
    detailViewModel: DetailSeanceViewModel = viewModel()
) {
    val dims = LocalAppDimensions.current
    val isFavori by favorisViewModel.isFavoriSeance(seanceId).collectAsState(initial = false)
    val seance by detailViewModel.seance.collectAsState()
    val exercices by detailViewModel.exercices.collectAsState()

    LaunchedEffect(seanceId) { detailViewModel.load(seanceId) }

    val nomSeance = seance?.nom ?: "Chargement..."
    val dureeMin = seance?.dureeMinutes ?: 0
    val niveau = seance?.niveau ?: ""

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.padding(horizontal = dims.horizontalPadding)) {
                Spacer(modifier = Modifier.height(dims.sectionSpacing))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onRetour) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextPrimary)
                    }
                    Text(
                        text = nomSeance,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { favorisViewModel.toggleFavoriSeance(seanceId) }) {
                        Icon(
                            imageVector = if (isFavori) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoris",
                            tint = if (isFavori) MaterialTheme.colorScheme.primary else TextPrimary
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.horizontalPadding)
                    .padding(bottom = 84.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (dureeMin > 0) {
                        InfoBadge(text = "⏱ $dureeMin min estimées", background = Color(0xFFECF3FD), textColor = Color(0xFF4A6FA5))
                    }
                    if (niveau.isNotBlank()) {
                        InfoBadge(text = "↑ $niveau", background = Color(0xFFE8F5E9), textColor = Color(0xFF2E7D32))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Exercices de la séance",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (exercices.isEmpty() && seance != null) {
                    Text(
                        text = "Aucun exercice dans cette séance.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                } else {
                    exercices.forEachIndexed { index, exo ->
                        ExerciceItem(
                            numero = index + 1,
                            nom = exo.nom,
                            isFirst = index == 0,
                            onClick = { onNavigateToDetailExercice(exo.exerciceId) }
                        )
                        if (index < exercices.lastIndex) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = dims.horizontalPadding, vertical = 16.dp)
        ) {
            Button(
                onClick = { if (exercices.isNotEmpty()) onCommencerSeance(seanceId) },
                enabled = exercices.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Commencer la séance", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun InfoBadge(text: String, background: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(background)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium, color = textColor)
    }
}

@Composable
private fun ExerciceItem(numero: Int, nom: String, isFirst: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(if (isFirst) TextPrimary else MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = numero.toString(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (isFirst) Color.White else TextSecondary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = nom, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
    }
}
