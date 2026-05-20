package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.theme.BadgeGreenBackground
import com.example.gymvision.ui.theme.BadgeGreenText
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

// Mock data — sera remplacé par Room
private data class ExerciceDetail(
    val nom: String,
    val dureeVideo: String,
    val description: String,
    val conseilsPosture: List<String>
)

private val mockExercices = mapOf(
    1 to ExerciceDetail(
        nom = "Tirage poulie haute",
        dureeVideo = "0:45",
        description = "Exercice fondamental pour le dos.\nIdéal pour développer la largeur et apprendre le recrutement musculaire, guidé de manière sécurisée par la machine.",
        conseilsPosture = listOf("Garder le dos droit", "Gainer les abdos")
    ),
    2 to ExerciceDetail(
        nom = "Rowing machine",
        dureeVideo = "0:38",
        description = "Exercice de tirage horizontal pour le dos et les biceps.\nPermet un bon recrutement des muscles du milieu du dos.",
        conseilsPosture = listOf("Garder le dos droit", "Ne pas bloquer les épaules", "Contrôler la descente")
    ),
    3 to ExerciceDetail(
        nom = "Curl haltères",
        dureeVideo = "0:30",
        description = "Exercice d'isolation pour les biceps.\nEffectuer une rotation du poignet en montant pour maximiser le travail.",
        conseilsPosture = listOf("Coudes fixes le long du corps", "Ne pas balancer le buste")
    ),
    4 to ExerciceDetail(
        nom = "Curl pupitre",
        dureeVideo = "0:35",
        description = "Isolation complète des biceps grâce au pupitre.\nElimine toute triche et cible directement le biceps.",
        conseilsPosture = listOf("Appuyer le bras sur le pupitre", "Descendre complètement")
    ),
)

@Composable
fun DetailExerciceScreen(
    exerciceId: Int,
    onRetour: () -> Unit,
    onCommencer: (Int) -> Unit
) {
    val dims = LocalAppDimensions.current
    val exercice = mockExercices[exerciceId] ?: mockExercices[1]!!

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
                        text = exercice.nom,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favoris", tint = TextPrimary)
                    }
                }
            }

            // Contenu scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.horizontalPadding)
                    .padding(bottom = 84.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Vignette vidéo
                VideoThumbnail(duree = exercice.dureeVideo)

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = exercice.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Conseils de postures
                Text(
                    text = "Conseils de postures",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                exercice.conseilsPosture.forEach { conseil ->
                    PostureBadge(text = conseil)
                    Spacer(modifier = Modifier.height(8.dp))
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
                onClick = { onCommencer(exerciceId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Commencer l'exercice",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun VideoThumbnail(duree: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFF2A2A2A))
    ) {
        // Bouton play centré
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f))
                .align(Alignment.Center)
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Lire",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }

        // Badge durée
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = duree,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PostureBadge(text: String) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(BadgeGreenBackground)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = BadgeGreenText,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = BadgeGreenText
        )
    }
}
