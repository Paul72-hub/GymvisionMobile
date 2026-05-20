package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.components.LogoGV
import com.example.gymvision.ui.theme.LocalAppDimensions
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary

@Composable
fun ProfilScreen(onNavigateToAccueil: () -> Unit) {
    val dims = LocalAppDimensions.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = dims.horizontalPadding)
    ) {
        Spacer(modifier = Modifier.height(dims.sectionSpacing))

        // Top bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            LogoGV(onClick = onNavigateToAccueil)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Mon Profil",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(dims.sectionSpacing))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(32.dp))

        // Email
        Text(text = "Email", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("nom@exemple.com", style = MaterialTheme.typography.bodyMedium, color = TextSecondary) },
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

        Spacer(modifier = Modifier.height(20.dp))

        // Mot de passe
        Text(text = "Mot de passe", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
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

        Spacer(modifier = Modifier.height(28.dp))

        // Bouton Se connecter
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Se connecter", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(24.dp))

        // Pas de compte
        Text(
            text = "Vous n'avez pas de compte ?",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Créer un compte", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mot de passe oublié
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Mot de passe oublié ? ", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            TextButton(onClick = {}, contentPadding = PaddingValues(0.dp)) {
                Text("Réinitialiser", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
