package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymvision.ui.theme.BackgroundDark
import com.example.gymvision.ui.theme.LocalAppDimensions

private val DarkCard    = Color(0xFF152232)
private val BlueAccent  = Color(0xFF4D9FFF)
private val TextWhite   = Color(0xFFFFFFFF)
private val TextMuted   = Color(0xFF8A9BB0)
private val DividerDark = Color(0xFF2A3D52)

private data class ExerciceSession(
    val nom: String,
    val repetitions: Int,
    val series: Int
)

private val mockSessions = mapOf(
    1 to ExerciceSession("Tirage poulie haute", 12, 4),
    2 to ExerciceSession("Rowing machine",      10, 4),
    3 to ExerciceSession("Curl haltères",       12, 3),
    4 to ExerciceSession("Curl pupitre",        10, 3),
)

@Composable
fun ExerciceCommenceScreen(
    exerciceId: Int,
    onRetour: () -> Unit
) {
    val dims = LocalAppDimensions.current
    val session = mockSessions[exerciceId] ?: mockSessions[1]!!

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top bar
            Spacer(modifier = Modifier.height(dims.sectionSpacing))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onRetour) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = TextWhite)
                }
                Text(
                    text = "Exercice commencé",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextWhite,
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(color = DividerDark)

            // Contenu centré
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = dims.horizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Nom de l'exercice
                Text(
                    text = session.nom,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Badge "En cours"
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF1A2F4A))
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(BlueAccent)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "En cours",
                        style = MaterialTheme.typography.labelMedium,
                        color = BlueAccent
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Card répétitions / séries
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(DarkCard)
                        .padding(vertical = 28.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Répétitions
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = session.repetitions.toString(),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "Répétitions",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextMuted
                            )
                        }

                        // Séparateur vertical
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(60.dp)
                                .background(DividerDark)
                        )

                        // Séries
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = session.series.toString(),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "Séries",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bouton Visualiser AR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(DarkCard)
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewInAr,
                        contentDescription = null,
                        tint = BlueAccent,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Visualiser exo AR",
                        style = MaterialTheme.typography.labelLarge,
                        color = BlueAccent
                    )
                }
            }
        }

        // Bouton fixe en bas
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BackgroundDark)
                .padding(horizontal = dims.horizontalPadding, vertical = 16.dp)
        ) {
            Button(
                onClick = onRetour,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Terminer la série",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
