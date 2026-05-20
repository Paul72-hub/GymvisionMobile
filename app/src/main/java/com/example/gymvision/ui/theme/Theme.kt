package com.example.gymvision.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    primaryContainer = PrimaryPurpleLight,
    background = BackgroundWhite,
    onBackground = TextPrimary,
    surface = SurfaceGray,
    onSurface = TextPrimary,
    outline = BorderColor
)

// Espacements responsives selon la taille d'écran
data class AppDimensions(
    val horizontalPadding: Dp,
    val cardSpacing: Dp,
    val sectionSpacing: Dp,
)

val CompactDimensions = AppDimensions(
    horizontalPadding = 16.dp,
    cardSpacing = 12.dp,
    sectionSpacing = 20.dp,
)

val MediumDimensions = AppDimensions(
    horizontalPadding = 24.dp,
    cardSpacing = 16.dp,
    sectionSpacing = 28.dp,
)

val LocalAppDimensions = compositionLocalOf { CompactDimensions }

@Composable
fun GymvisionTheme(
    dimensions: AppDimensions = CompactDimensions,
    content: @Composable () -> Unit
) {
    val typography = if (dimensions == CompactDimensions) CompactTypography else Typography
    CompositionLocalProvider(LocalAppDimensions provides dimensions) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}
