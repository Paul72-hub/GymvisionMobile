package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

private data class FavoriExercice(val id: Int, val nom: String)
private data class FavoriSeance(val id: Int, val nom: String, val icon: ImageVector, val iconBg: Color, val iconTint: Color)

private val mockFavExercices = listOf(
    FavoriExercice(2, "Rowing machine"),
    FavoriExercice(3, "Curl haltères"),
    FavoriExercice(4, "Curl pupitre"),
)
private val mockFavSeances = listOf(
    FavoriSeance(1, "Dos & Biceps", Icons.Default.FitnessCenter, Color(0xFFDCEEFD), Color(0xFF1976D2)),
    FavoriSeance(3, "Pectoraux",    Icons.Default.Favorite,      Color(0xFFFFE4E8), Color(0xFFE53935)),
)

@Composable
fun FavorisScreen(
    onRetour: () -> Unit,
    onNavigateToDetailExercice: (Int) -> Unit,
    onNavigateToDetailSeance: (Int) -> Unit
) {
    val dims = LocalAppDimensions.current
    var tabSelectionne by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = dims.horizontalPadding)
    ) {
        Spacer(modifier = Modifier.height(dims.sectionSpacing))

        // Top bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onRetour) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextPrimary)
            }
            Text(
                text = "Favoris",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs custom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
        ) {
            listOf("Exercices", "Séances").forEachIndexed { index, label ->
                val selected = tabSelectionne == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.small)
                        .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .clickable { tabSelectionne = index }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) Color.White else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tabSelectionne == 0) {
            // Onglet Exercices
            Text(
                text = "Mes favoris(${mockFavExercices.size})",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            mockFavExercices.forEach { fav ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToDetailExercice(fav.id) }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(fav.nom, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.MoreVert, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            }
        } else {
            // Onglet Séances
            Text(
                text = "Mes favoris(${mockFavSeances.size})",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            mockFavSeances.forEach { fav ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                        .clickable { onNavigateToDetailSeance(fav.id) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(36.dp).clip(MaterialTheme.shapes.extraSmall).background(fav.iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(fav.icon, contentDescription = null, tint = fav.iconTint, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(fav.nom, style = MaterialTheme.typography.titleMedium, color = TextPrimary, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Créer ma propre séance
        val dashedColor = MaterialTheme.colorScheme.outline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .drawBehind {
                    drawRoundRect(
                        color = dashedColor,
                        style = Stroke(width = 1.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10.dp.toPx(), 6.dp.toPx()), 0f)),
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text("Créer ma propre séance", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
