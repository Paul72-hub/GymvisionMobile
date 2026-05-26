package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymvision.ui.components.LogoGV
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.example.gymvision.ui.viewmodel.RechercherViewModel

private val muscles = listOf("Dos", "Pectoraux", "Biceps", "Épaules", "Jambes", "Abdos")
private val niveaux = listOf("Débutant", "Intermédiaire", "Confirmé")

@Composable
fun RechercherScreen(
    onNavigateToDetailExercice: (Int) -> Unit,
    onNavigateToAccueil: () -> Unit,
    selectionMode: Boolean = false,
    onExerciceSelectionne: ((Int) -> Unit)? = null,
    viewModel: RechercherViewModel = viewModel()
) {
    val dims = LocalAppDimensions.current
    var searchQuery by remember { mutableStateOf("") }
    val selectedMuscles = remember { mutableStateListOf<String>() }
    var selectedNiveau by remember { mutableStateOf<String?>(null) }

    val tousExercices by viewModel.exercices.collectAsState(initial = emptyList())

    val resultats = remember(tousExercices, searchQuery, selectedMuscles.toList(), selectedNiveau) {
        tousExercices.filter { exo ->
            val matchSearch = searchQuery.isBlank() || exo.nom.contains(searchQuery, ignoreCase = true)
            val matchMuscle = selectedMuscles.isEmpty() || selectedMuscles.any { exo.muscle.contains(it, ignoreCase = true) }
            val matchNiveau = selectedNiveau == null || exo.niveau.equals(selectedNiveau, ignoreCase = true)
            matchSearch && matchMuscle && matchNiveau
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dims.horizontalPadding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = if (selectionMode) 0.dp else 84.dp)
        ) {
            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!selectionMode) {
                    LogoGV(onClick = onNavigateToAccueil)
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = if (selectionMode) "Choisir un exercice" else "Rechercher",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Tirage, squat, dos...", style = MaterialTheme.typography.bodyMedium, color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tune, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Filtre", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Muscle ciblé", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(10.dp))

            // Grille de 3 colonnes pour les muscles
            val muscleRows = muscles.chunked(3)
            muscleRows.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { muscle ->
                        val selected = muscle in selectedMuscles
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(MaterialTheme.shapes.small)
                                .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
                                .background(if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                                .clickable { if (selected) selectedMuscles.remove(muscle) else selectedMuscles.add(muscle) }
                                .padding(horizontal = 8.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(muscle, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Niveau de difficulté", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(10.dp))
            niveaux.forEach { niveau ->
                val selected = selectedNiveau == niveau
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
                        .clickable { selectedNiveau = if (selected) null else niveau }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected,
                        onClick = { selectedNiveau = if (selected) null else niveau },
                        modifier = Modifier.size(18.dp),
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(niveau, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Résultats (${resultats.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            resultats.forEach { exo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                        .clickable {
                            if (selectionMode) {
                                onExerciceSelectionne?.invoke(exo.id)
                            } else {
                                onNavigateToDetailExercice(exo.id)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(exo.nom, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                        Text(exo.muscle, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                    Icon(
                        imageVector = if (selectionMode) Icons.Default.Add else Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = if (selectionMode) MaterialTheme.colorScheme.primary else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
