package com.example.gymvision.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymvision.data.model.Seance
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.example.gymvision.ui.viewmodel.SeancesViewModel

private fun iconForSeance(nom: String): Triple<ImageVector, Color, Color> = when {
    nom.contains("Dos", ignoreCase = true) || nom.contains("Biceps", ignoreCase = true) ->
        Triple(Icons.Default.FitnessCenter, Color(0xFFDCEEFD), Color(0xFF1976D2))
    nom.contains("Jambes", ignoreCase = true) ->
        Triple(Icons.Default.DirectionsRun, Color(0xFFFFF9C4), Color(0xFFF9A825))
    nom.contains("Pectoraux", ignoreCase = true) ->
        Triple(Icons.Default.Favorite, Color(0xFFFFE4E8), Color(0xFFE53935))
    nom.contains("paule", ignoreCase = true) || nom.contains("Abdos", ignoreCase = true) ->
        Triple(Icons.Default.LocalFireDepartment, Color(0xFFFFF0E0), Color(0xFFE65100))
    else ->
        Triple(Icons.Default.FitnessCenter, Color(0xFFE8EAF6), Color(0xFF5C6BC0))
}

@Composable
fun SeancesScreen(
    onNavigateToDetailSeance: (Int) -> Unit,
    onNavigateToNouvelleSeance: () -> Unit,
    onRetour: () -> Unit,
    viewModel: SeancesViewModel = viewModel()
) {
    val dims = LocalAppDimensions.current
    val seances by viewModel.seances.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = dims.horizontalPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(dims.sectionSpacing))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(dims.sectionSpacing))
            IconButton(onClick = onRetour) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextPrimary)
            }
            Text(
                text = "Séances",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(dims.sectionSpacing))

        Text(
            text = "Séances disponibles",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        seances.forEach { seance ->
            SeanceCard(seance = seance, onClick = { onNavigateToDetailSeance(seance.id) })
            Spacer(modifier = Modifier.height(dims.cardSpacing))
        }

        Spacer(modifier = Modifier.height(8.dp))
        CreerSeanceButton(onClick = onNavigateToNouvelleSeance)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SeanceCard(seance: Seance, onClick: () -> Unit) {
    val (icon, bgColor, tintColor) = iconForSeance(seance.nom)
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = tintColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = seance.nom, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                if (seance.niveau.isNotBlank()) {
                    Text(text = seance.niveau, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun CreerSeanceButton(onClick: () -> Unit) {
    val dashedColor = MaterialTheme.colorScheme.outline
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .drawBehind {
                drawRoundRect(
                    color = dashedColor,
                    style = Stroke(width = 1.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10.dp.toPx(), 6.dp.toPx()), 0f)),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(34.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Créer ma propre séance", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        }
    }
}
