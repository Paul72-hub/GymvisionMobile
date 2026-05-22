package com.example.gymvision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymvision.ui.components.LogoGV
import com.example.gymvision.ui.theme.TextPrimary
import com.example.gymvision.ui.theme.TextSecondary
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoginMode by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val auth = remember { FirebaseAuth.getInstance() }

    fun submit() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Veuillez remplir tous les champs."
            return
        }
        isLoading = true
        errorMessage = null
        val task = if (isLoginMode) {
            auth.signInWithEmailAndPassword(email.trim(), password)
        } else {
            auth.createUserWithEmailAndPassword(email.trim(), password)
        }
        task.addOnSuccessListener {
            isLoading = false
            onLoginSuccess()
        }.addOnFailureListener { e ->
            isLoading = false
            errorMessage = when {
                e.message?.contains("email") == true -> "Adresse e-mail invalide."
                e.message?.contains("password") == true -> "Mot de passe trop court (6 caractères min)."
                e.message?.contains("no user") == true || e.message?.contains("INVALID_LOGIN") == true -> "Identifiants incorrects."
                e.message?.contains("already in use") == true -> "Cette adresse e-mail est déjà utilisée."
                else -> "Erreur : ${e.message}"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo + titre
        Row(verticalAlignment = Alignment.CenterVertically) {
            LogoGV()
            Spacer(modifier = Modifier.width(10.dp))
            Text("GymVision", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isLoginMode) "Connexion" else "Créer un compte",
            style = MaterialTheme.typography.titleMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = null },
            label = { Text("Adresse e-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = null },
            label = { Text("Mot de passe") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        // Message d'erreur
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bouton principal
        Button(
            onClick = { submit() },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text(if (isLoginMode) "Se connecter" else "Créer le compte")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle login ↔ register
        TextButton(onClick = { isLoginMode = !isLoginMode; errorMessage = null }) {
            Text(
                text = if (isLoginMode) "Pas de compte ? Créer un compte" else "Déjà un compte ? Se connecter",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
