package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatListBulleted
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

@Composable
fun NouvelleSeanceScreen(
    onRetour: () -> Unit,
    onNavigateToRechercher: () -> Unit
) {
    val dims = LocalAppDimensions.current
    var nomSeance by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dims.horizontalPadding)
        ) {
            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            // Top bar
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRetour) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextPrimary)
                }
                Text(
                    text = "Nouvelle Séance",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(24.dp))

            // Nom de la séance
            Text(text = "Séances proposées", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = nomSeance,
                onValueChange = { nomSeance = it },
                placeholder = { Text("Ex: Push Day, Haut du corps...", style = MaterialTheme.typography.bodyMedium, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Exercices sélectionnés
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Exercices sélectionnés", style = MaterialTheme.typography.bodyMedium, color = TextPrimary, modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "0 exercice", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Zone vide avec bordure pointillée
            val dashedColor = MaterialTheme.colorScheme.outline
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = dashedColor,
                            style = Stroke(width = 1.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10.dp.toPx(), 6.dp.toPx()), 0f)),
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FormatListBulleted,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Votre séance est\nvide pour le moment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bouton Ajouter un exercice
            Button(
                onClick = onNavigateToRechercher,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ) {
                Text("+ Ajouter un exercice", style = MaterialTheme.typography.labelLarge, color = Color.White)
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
                onClick = onRetour,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Enregistrer la séance", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
