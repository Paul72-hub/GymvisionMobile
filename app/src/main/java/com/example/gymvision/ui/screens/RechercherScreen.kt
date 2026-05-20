package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.components.LogoGV
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

private val muscles = listOf("Dos", "Pectoraux", "Biceps", "Triceps", "Jambes", "Épaules")
private val niveaux = listOf("Débutant", "Intermédiaire", "Confirmé")
private val mockExercices = listOf(
    Pair(1, "Tirage Poulie Haute"),
    Pair(2, "Développé couché"),
    Pair(3, "Rowing machine"),
    Pair(4, "Curl haltères"),
)

@Composable
fun RechercherScreen(
    onNavigateToDetailExercice: (Int) -> Unit,
    onNavigateToAccueil: () -> Unit
) {
    val dims = LocalAppDimensions.current
    var searchQuery by remember { mutableStateOf("") }
    val selectedMuscles = remember { mutableStateListOf<String>() }
    var selectedNiveau by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dims.horizontalPadding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 84.dp)
        ) {
            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            // Top bar
            Row(verticalAlignment = Alignment.CenterVertically) {
                LogoGV(onClick = onNavigateToAccueil)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Rechercher",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            // Barre de recherche
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

            // Section Filtre
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tune, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Filtre", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muscle ciblé
            Text(text = "Muscle ciblé", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                muscles.take(2).forEach { muscle ->
                    val selected = muscle in selectedMuscles
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
                            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                            .clickable {
                                if (selected) selectedMuscles.remove(muscle)
                                else selectedMuscles.add(muscle)
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selected,
                            onCheckedChange = null,
                            modifier = Modifier.size(16.dp),
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(muscle, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Niveau de difficulté
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

            // Section Résultats
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Résultats", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            val resultats = if (searchQuery.isBlank() && selectedMuscles.isEmpty() && selectedNiveau == null)
                mockExercices
            else
                mockExercices.filter { (_, nom) ->
                    nom.contains(searchQuery, ignoreCase = true)
                }

            resultats.forEach { (id, nom) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                        .clickable { onNavigateToDetailExercice(id) }
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(nom, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Bouton fixe
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = dims.horizontalPadding, vertical = 16.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Appliquer les filtres", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
