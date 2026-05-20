package com.example.gymvision.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),   // avatar logo, petits badges
    small = RoundedCornerShape(12.dp),        // champs texte, boutons filtre
    medium = RoundedCornerShape(16.dp),       // cards
    large = RoundedCornerShape(20.dp),        // bottom sheets
    extraLarge = RoundedCornerShape(28.dp)    // boutons CTA principaux
)
