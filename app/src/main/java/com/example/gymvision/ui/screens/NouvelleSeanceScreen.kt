package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.example.gymvision.ui.viewmodel.NouvelleSeanceViewModel

@Composable
fun NouvelleSeanceScreen(
    viewModel: NouvelleSeanceViewModel,
    onRetour: () -> Unit,
    onAjouterExercice: () -> Unit
) {
    val dims = LocalAppDimensions.current

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dims.horizontalPadding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 84.dp)
        ) {
            Spacer(modifier = Modifier.height(dims.sectionSpacing))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRetour) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextPrimary)
                }
                Text(text = "Nouvelle Séance", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Nom de la séance", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.nom,
                onValueChange = { viewModel.updateNom(it) },
                placeholder = { Text("Ex: Push Day, Haut du corps...", style = MaterialTheme.typography.bodyMedium, color = TextSecondary) },
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Exercices sélectionnés",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    val count = viewModel.exercices.size
                    Text(
                        text = "$count exercice${if (count > 1) "s" else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.exercices.isEmpty()) {
                val dashedColor = MaterialTheme.colorScheme.outline
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
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
                        Icon(Icons.Default.FormatListBulleted, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Votre séance est\nvide pour le moment.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                viewModel.exercices.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.exercice.nom, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                            Text(
                                "${item.series} séries × ${item.repetitions} reps",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        IconButton(
                            onClick = { viewModel.retirerExercice(item.exercice.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Retirer", tint = TextSecondary, modifier = Modifier.size(18.dp))
                        }
                    }
                    if (index < viewModel.exercices.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onAjouterExercice,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ) {
                Text("+ Ajouter un exercice", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        // Bouton Enregistrer fixe en bas
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = dims.horizontalPadding, vertical = 16.dp)
        ) {
            Button(
                onClick = { viewModel.sauvegarder { onRetour() } },
                enabled = viewModel.canSave,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Enregistrer la séance", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
