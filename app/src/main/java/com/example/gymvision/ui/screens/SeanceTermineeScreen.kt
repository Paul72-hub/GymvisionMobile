package com.example.gymvision.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymvision.ui.theme.BackgroundDark
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private val BlueAccent = Color(0xFF4D9FFF)

private val particleColors = listOf(
    Color(0xFFFFD700), Color(0xFF4D9FFF), Color(0xFF4CAF50),
    Color(0xFFE91E63), Color(0xFFFF9800), Color(0xFF9C27B0)
)

@Composable
fun SeanceTermineeScreen(onRetour: () -> Unit) {
    var started by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { started = true }

    val iconScale by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "iconScale"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = tween(durationMillis = 500, delayMillis = 400),
        label = "contentAlpha"
    )

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {

            // Trophée avec éclat radial
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                // Particules radiales
                val angles = listOf(0f, 60f, 120f, 180f, 240f, 300f)
                angles.forEachIndexed { i, angleDeg ->
                    val distance by animateFloatAsState(
                        targetValue = if (started) 85f else 0f,
                        animationSpec = tween(
                            durationMillis = 450,
                            delayMillis = 150 + i * 40,
                            easing = FastOutSlowInEasing
                        ),
                        label = "particle$i"
                    )
                    val particleAlpha by animateFloatAsState(
                        targetValue = if (started) 1f else 0f,
                        animationSpec = tween(250, delayMillis = 150 + i * 40),
                        label = "particleAlpha$i"
                    )
                    val rad = Math.toRadians(angleDeg.toDouble())
                    val px = (cos(rad) * distance).roundToInt()
                    val py = (sin(rad) * distance).roundToInt()

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset { IntOffset(px, py) }
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(particleColors[i].copy(alpha = particleAlpha))
                    )
                }

                // Halo externe
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .scale(iconScale)
                        .clip(CircleShape)
                        .background(BlueAccent.copy(alpha = 0.12f))
                )

                // Cercle central
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .scale(iconScale)
                        .clip(CircleShape)
                        .background(BlueAccent.copy(alpha = 0.22f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = BlueAccent,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Séance terminée !",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Bravo ! Tu as complété\ntous les exercices.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8A9BB0),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            )

            Spacer(Modifier.height(48.dp))

            Button(
                onClick = onRetour,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .graphicsLayer { alpha = contentAlpha },
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = BlueAccent)
            ) {
                Text(
                    text = "Retour à la séance",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
