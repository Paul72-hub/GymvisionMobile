package com.example.gymvision.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Grand écran (tablette, grand téléphone)
val Typography = Typography(
    titleLarge  = TextStyle(fontWeight = FontWeight.Bold,     fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.sp),
    titleSmall  = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.sp),
    bodyLarge   = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp),
    bodyMedium  = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.sp),
    bodySmall   = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
    labelLarge  = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium,   fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.sp),
    labelSmall  = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.sp),
)

// Petit écran : tout réduit d'environ 2sp
val CompactTypography = Typography(
    titleLarge  = TextStyle(fontWeight = FontWeight.Bold,     fontSize = 18.sp, lineHeight = 24.sp, letterSpacing = 0.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 22.sp, letterSpacing = 0.sp),
    titleSmall  = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
    bodyLarge   = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 15.sp, lineHeight = 22.sp, letterSpacing = 0.15.sp),
    bodyMedium  = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
    bodySmall   = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.sp),
    labelLarge  = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium,   fontSize = 11.sp, lineHeight = 15.sp, letterSpacing = 0.sp),
    labelSmall  = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 10.sp, lineHeight = 14.sp, letterSpacing = 0.sp),
)
