package com.kavyakanaja.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Parchment Palette ────────────────────────────────────────────
val Parchment        = Color(0xFFFDF6E3)   // warm off-white
val ParchmentDark    = Color(0xFFF5EDD0)   // slightly darker parchment
val InkBrown         = Color(0xFF3E2723)   // deep brown — main text
val InkLight         = Color(0xFF6D4C41)   // medium brown
val SaffronGold      = Color(0xFFF59E0B)   // accent gold
val SaffronDark      = Color(0xFFB45309)   // deeper saffron
val DeepGreen        = Color(0xFF2D6A4F)   // poetry green accent
val CrimsonRed       = Color(0xFF9B1B1B)   // highlighted word colour
val CardSurface      = Color(0xFFFFF8E8)   // card background
val StarGold         = Color(0xFFFFB300)

private val KavyaColorScheme = lightColorScheme(
    primary          = SaffronDark,
    onPrimary        = Color.White,
    primaryContainer = SaffronGold,
    onPrimaryContainer = InkBrown,
    secondary        = DeepGreen,
    onSecondary      = Color.White,
    background       = Parchment,
    onBackground     = InkBrown,
    surface          = CardSurface,
    onSurface        = InkBrown,
    surfaceVariant   = ParchmentDark,
    onSurfaceVariant = InkLight,
    error            = CrimsonRed,
    onError          = Color.White,
    outline          = InkLight
)

@Composable
fun KavyaKanajaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KavyaColorScheme,
        typography  = KavyaTypography,
        content     = content
    )
}

// ── Typography ───────────────────────────────────────────────────
val KavyaTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.Bold,
        fontSize     = 32.sp,
        lineHeight   = 42.sp,
        letterSpacing = 0.5.sp,
        color        = InkBrown
    ),
    displayMedium = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.Bold,
        fontSize     = 26.sp,
        lineHeight   = 36.sp,
        color        = InkBrown
    ),
    headlineLarge = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 22.sp,
        lineHeight   = 30.sp,
        color        = InkBrown
    ),
    headlineMedium = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 18.sp,
        lineHeight   = 26.sp,
        color        = InkBrown
    ),
    bodyLarge = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.Normal,
        fontSize     = 18.sp,
        lineHeight   = 30.sp,
        letterSpacing = 0.3.sp,
        color        = InkBrown
    ),
    bodyMedium = TextStyle(
        fontFamily   = FontFamily.Serif,
        fontWeight   = FontWeight.Normal,
        fontSize     = 15.sp,
        lineHeight   = 24.sp,
        color        = InkLight
    ),
    labelLarge = TextStyle(
        fontFamily   = FontFamily.SansSerif,
        fontWeight   = FontWeight.Medium,
        fontSize     = 14.sp,
        color        = InkBrown
    ),
    labelMedium = TextStyle(
        fontFamily   = FontFamily.SansSerif,
        fontWeight   = FontWeight.Normal,
        fontSize     = 12.sp,
        color        = InkLight
    )
)
