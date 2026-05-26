package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.components.LogoGV
import com.example.gymvision.ui.theme.BackgroundDark
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.PrimaryPurple
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

@Composable
fun AccueilScreen(
    onNavigateToSeances: () -> Unit,
    onNavigateToRechercher: () -> Unit,
    onNavigateToFavoris: () -> Unit,
    onNavigateToQrScanner: () -> Unit = {},
    onNavigateToProfil: () -> Unit = {}
) {
    val dims = LocalAppDimensions.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = dims.horizontalPadding)
    ) {
        Spacer(modifier = Modifier.height(dims.sectionSpacing))

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoGV()
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "GymVision",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onNavigateToProfil) {
                Text("Se connecter", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(dims.sectionSpacing))

        // Barre de recherche (raccourci vers RechercherScreen)
        Box(modifier = Modifier.fillMaxWidth().clickable { onNavigateToRechercher() }) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                enabled = false,
                placeholder = { Text("Rechercher un exercice...", style = MaterialTheme.typography.bodyMedium, color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledPlaceholderColor = TextSecondary,
                    disabledLeadingIconColor = TextSecondary,
                    disabledTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(dims.sectionSpacing + 8.dp))

        // Cards
        AccueilCard(
            icon = Icons.Default.QrCodeScanner,
            iconBackground = Color(0xFFE3F2FD),
            iconTint = Color(0xFF1976D2),
            title = "Scanner QR code",
            description = "Découvrez comment utiliser une machine instantanément.",
            onClick = onNavigateToQrScanner
        )
        Spacer(modifier = Modifier.height(dims.cardSpacing))
        AccueilCard(
            icon = Icons.Default.FitnessCenter,
            iconBackground = Color(0xFFE8EAF6),
            iconTint = MaterialTheme.colorScheme.primary,
            title = "SéanceType",
            description = "Créer votre séance personnalisée et suivez vos progrès.",
            onClick = onNavigateToSeances
        )
        Spacer(modifier = Modifier.height(dims.cardSpacing))
        AccueilCard(
            icon = Icons.Default.Favorite,
            iconBackground = Color(0xFFFCE4EC),
            iconTint = Color(0xFFE91E63),
            title = "Favoris",
            description = "Accéder à vos exercices/séances favorites",
            onClick = onNavigateToFavoris
        )
    }
}

@Composable
private fun AccueilCard(
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = PrimaryPurple.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = BackgroundDark
                )
            }
        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    AccueilScreen({}, {}, {})
}
