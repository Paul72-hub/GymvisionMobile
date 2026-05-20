package com.example.gymvision.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoGV(onClick: (() -> Unit)? = null) {
    val modifier = Modifier
        .size(36.dp)
        .clip(MaterialTheme.shapes.extraSmall)
        .background(Color(0xFF504BD7).copy(alpha = 0.8f))
        .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)) {
                    append("G")
                }
                withStyle(SpanStyle(color = Color(0xFF650DFF), fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, letterSpacing = (-5).sp)) {
                    append("V")
                }
            }
        )
    }
}
