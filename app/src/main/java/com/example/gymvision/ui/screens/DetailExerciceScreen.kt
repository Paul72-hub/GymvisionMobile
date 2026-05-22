package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gymvision.ui.theme.BadgeGreenBackground
import com.example.gymvision.ui.viewmodel.FavorisViewModel
import com.example.gymvision.ui.theme.BadgeGreenText
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

// Mock data — sera remplacé par Room
// Pour les youtubeVideoId : ouvre YouTube, trouve une vidéo tuto, copie l'ID depuis l'URL
// Ex: youtube.com/watch?v=ABC123XYZ → videoId = "ABC123XYZ"
private data class ExerciceDetail(
    val nom: String,
    val description: String,
    val conseilsPosture: List<String>,
    val youtubeVideoId: String = ""
)

private val mockExercices = mapOf(
    // Séance 1 — Dos & Biceps
    1 to ExerciceDetail(
        nom = "Tirage poulie haute",
        description = "Exercice fondamental pour le dos.\nIdéal pour développer la largeur et apprendre le recrutement musculaire, guidé de manière sécurisée par la machine.",
        conseilsPosture = listOf("Garder le dos droit", "Gainer les abdos"),
        youtubeVideoId = "BHQ692ZKMRI"
    ),
    2 to ExerciceDetail(
        nom = "Rowing machine",
        description = "Exercice de tirage horizontal pour le dos et les biceps.\nPermet un bon recrutement des muscles du milieu du dos.",
        conseilsPosture = listOf("Garder le dos droit", "Ne pas bloquer les épaules", "Contrôler la descente"),
        youtubeVideoId = "xkaX66b1oA0"
    ),
    3 to ExerciceDetail(
        nom = "Curl haltères",
        description = "Exercice d'isolation pour les biceps.\nEffectuer une rotation du poignet en montant pour maximiser le travail.",
        conseilsPosture = listOf("Coudes fixes le long du corps", "Ne pas balancer le buste"),
        youtubeVideoId = "bxyocI5XBoA"
    ),
    4 to ExerciceDetail(
        nom = "Curl pupitre",
        description = "Isolation complète des biceps grâce au pupitre.\nElimine toute triche et cible directement le biceps.",
        conseilsPosture = listOf("Appuyer le bras sur le pupitre", "Descendre complètement"),
        youtubeVideoId = "P13VQDU1q9c"
    ),
    // Séance 2 — Jambes
    5 to ExerciceDetail(
        nom = "Squat barre",
        description = "Exercice roi du bas du corps.\nSollicite quadriceps, ischio-jambiers, fessiers et dos. Essentiel pour développer la force et la masse des jambes.",
        conseilsPosture = listOf("Pieds à largeur d'épaules", "Genoux dans l'axe des pieds", "Dos droit, poitrine haute"),
        youtubeVideoId = "WVkMZOPtid4"
    ),
    6 to ExerciceDetail(
        nom = "Leg press",
        description = "Exercice de poussée des jambes à la machine.\nPermet de charger lourd en toute sécurité, idéal pour cibler les quadriceps et les fessiers.",
        conseilsPosture = listOf("Pieds à mi-hauteur de la plateforme", "Ne pas verrouiller les genoux", "Dos plaqué sur le dossier"),
        youtubeVideoId = "K5n2vg3oZa4"
    ),
    7 to ExerciceDetail(
        nom = "Fentes marchées",
        description = "Exercice unilatéral pour les jambes et les fessiers.\nCorrige les déséquilibres musculaires et améliore la coordination.",
        conseilsPosture = listOf("Grand pas en avant", "Genou arrière proche du sol", "Buste droit tout au long"),
        youtubeVideoId = "HH9FQpjXw7A"
    ),
    8 to ExerciceDetail(
        nom = "Leg curl",
        description = "Exercice d'isolation pour les ischio-jambiers.\nIndispensable en fin de séance jambes pour équilibrer le travail des quadriceps.",
        conseilsPosture = listOf("Ne pas décoller les hanches", "Contrôler la remontée", "Amplitude complète"),
        youtubeVideoId = "l9QbFtlkjCw"
    ),
    // Séance 3 — Pectoraux
    9 to ExerciceDetail(
        nom = "Développé couché",
        description = "Exercice de base pour les pectoraux.\nSollicite également les épaules et les triceps. Incontournable pour développer la force et le volume du buste.",
        conseilsPosture = listOf("Barre à hauteur des tétons", "Coudes à 45° du corps", "Pieds à plat au sol"),
        youtubeVideoId = "feWp7jZopI8"
    ),
    10 to ExerciceDetail(
        nom = "Écarté haltères",
        description = "Exercice d'isolation pour les pectoraux.\nExcellent étirement en bas du mouvement, cible les fibres difficiles à atteindre avec la presse.",
        conseilsPosture = listOf("Légère flexion des coudes", "Descendre jusqu'au niveau des épaules", "Contrôler la montée"),
        youtubeVideoId = "lynnbMeaMcE"
    ),
    11 to ExerciceDetail(
        nom = "Pompes",
        description = "Exercice au poids du corps pour les pectoraux, épaules et triceps.\nSimple et efficace, parfait pour finir une séance pectoraux.",
        conseilsPosture = listOf("Corps en ligne droite", "Coudes à 45°", "Descendre la poitrine jusqu'au sol"),
        youtubeVideoId = "-lkv4E8Aymk"
    ),
    12 to ExerciceDetail(
        nom = "Croisé poulie",
        description = "Exercice de finition pour les pectoraux à la poulie.\nTension constante tout au long du mouvement, idéal pour la définition musculaire.",
        conseilsPosture = listOf("Légère inclinaison du buste", "Croiser les mains en fin de mouvement", "Ne pas bloquer les coudes"),
        youtubeVideoId = "6b6jE69AkIs"
    ),
    // Séance 4 — Épaule & Abdos
    13 to ExerciceDetail(
        nom = "Élévation latérale",
        description = "Exercice d'isolation pour le faisceau moyen des deltoïdes.\nEssentiel pour élargir les épaules et obtenir un aspect en V.",
        conseilsPosture = listOf("Légère flexion des coudes", "Monter jusqu'à l'horizontale", "Ne pas balancer le corps"),
        youtubeVideoId = "7L8KCZMj6_g"
    ),
    14 to ExerciceDetail(
        nom = "Développé militaire",
        description = "Exercice polyarticulaire pour les épaules.\nSollicite les trois faisceaux des deltoïdes ainsi que les trapèzes et les triceps.",
        conseilsPosture = listOf("Gainer les abdos", "Barre devant le visage", "Pousser sans cambrer le dos"),
        youtubeVideoId = "t7ZU2bG49ZE"
    ),
    15 to ExerciceDetail(
        nom = "Crunch",
        description = "Exercice de base pour les abdominaux.\nCible le droit de l'abdomen. Privilégier la qualité à la quantité et bien contracter au sommet.",
        conseilsPosture = listOf("Mains derrière la nuque sans tirer", "Décoller uniquement les épaules", "Expirer à la montée"),
        youtubeVideoId = "7ZwodZ1B0s4"
    ),
    16 to ExerciceDetail(
        nom = "Gainage",
        description = "Exercice isométrique pour les abdominaux profonds.\nRenforce le core et protège le bas du dos. Excellent pour la posture globale.",
        conseilsPosture = listOf("Corps aligné tête-pieds", "Ne pas laisser les hanches tomber", "Respirer régulièrement"),
        youtubeVideoId = "uXSljAP_kJU"
    ),
)

@Composable
fun DetailExerciceScreen(
    exerciceId: Int,
    onRetour: () -> Unit,
    onCommencer: (Int) -> Unit,
    favorisViewModel: FavorisViewModel = viewModel()
) {
    val dims = LocalAppDimensions.current
    val exercice = mockExercices[exerciceId] ?: mockExercices[1]!!
    val isFavori by favorisViewModel.isFavoriExercice(exerciceId).collectAsState(initial = false)

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
                    IconButton(onClick = { favorisViewModel.toggleFavoriExercice(exerciceId) }) {
                        Icon(
                            imageVector = if (isFavori) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoris",
                            tint = if (isFavori) MaterialTheme.colorScheme.primary else TextPrimary
                        )
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

                // Player YouTube
                VideoPlayer(videoId = exercice.youtubeVideoId)

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
private fun VideoPlayer(videoId: String) {
    val cleanId = videoId.removePrefix("shorts/").trim()
    if (cleanId.isEmpty()) {
        VideoThumbnailPlaceholder()
        return
    }
    val context = LocalContext.current
    val thumbnailUrl = "https://img.youtube.com/vi/$cleanId/hqdefault.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$cleanId"))
                context.startActivity(intent)
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Voir sur YouTube",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun VideoThumbnailPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFF2A2A2A)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Lire",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
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
