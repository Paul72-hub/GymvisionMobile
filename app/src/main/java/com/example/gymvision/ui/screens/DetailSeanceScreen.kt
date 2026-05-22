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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.example.gymvision.ui.viewmodel.FavorisViewModel

// Mock data — sera remplacé par Room
private data class SeanceDetail(
    val nom: String,
    val duree: String,
    val niveau: String,
    val exercices: List<String>
)

private val mockSeances = mapOf(
    1 to SeanceDetail("Dos & Biceps", "50 min estimées", "Niveau débutant",
        listOf("Tirage poulie haute", "Rowing machine", "Curl haltères", "Curl pupitre")),
    2 to SeanceDetail("Jambes", "45 min estimées", "Niveau débutant",
        listOf("Squat barre", "Leg press", "Fentes marchées", "Leg curl")),
    3 to SeanceDetail("Pectoraux", "50 min estimées", "Niveau intermédiaire",
        listOf("Développé couché", "Écarté haltères", "Pompes", "Croisé poulie")),
    4 to SeanceDetail("Epaule & Abdos", "40 min estimées", "Niveau débutant",
        listOf("Élévation latérale", "Développé militaire", "Crunch", "Gainage")),
)

@Composable
fun DetailSeanceScreen(
    seanceId: Int,
    onRetour: () -> Unit,
    onNavigateToDetailExercice: (Int) -> Unit,
    favorisViewModel: FavorisViewModel = viewModel()
) {
    val dims = LocalAppDimensions.current
    val seance = mockSeances[seanceId] ?: mockSeances[1]!!
    val isFavori by favorisViewModel.isFavoriSeance(seanceId).collectAsState(initial = false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top bar
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
                        text = seance.nom,
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

            // Contenu scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.horizontalPadding)
                    .padding(bottom = 84.dp) // espace pour le bouton fixe
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Badges durée / niveau
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBadge(
                        text = "⏱ ${seance.duree}",
                        background = Color(0xFFECF3FD),
                        textColor = Color(0xFF4A6FA5)
                    )
                    InfoBadge(
                        text = "↑ ${seance.niveau}",
                        background = Color(0xFFE8F5E9),
                        textColor = Color(0xFF2E7D32)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Exercices de la séance",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                seance.exercices.forEachIndexed { index, exercice ->
                    ExerciceItem(
                        numero = index + 1,
                        nom = exercice,
                        isFirst = index == 0,
                        onClick = { onNavigateToDetailExercice((seanceId - 1) * 4 + index + 1) }
                    )
                    if (index < seance.exercices.lastIndex) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        // Bouton fixe en bas
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = dims.horizontalPadding, vertical = 16.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Commencer la séance",
                    style = MaterialTheme.typography.labelLarge
                )
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
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}

@Composable
private fun ExerciceItem(
    numero: Int,
    nom: String,
    isFirst: Boolean,
    onClick: () -> Unit
) {
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
                .background(
                    if (isFirst) TextPrimary
                    else MaterialTheme.colorScheme.surface
                ),
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
        Text(
            text = nom,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}
@Preview
@Composable
fun DetailSeanceScreen() {
    DetailSeanceScreen(5,{},{})
}