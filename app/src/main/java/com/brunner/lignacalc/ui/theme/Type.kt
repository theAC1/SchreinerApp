package com.brunner.lignacalc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.brunner.lignacalc.R

// Google Fonts Provider
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Fraunces — Serif Display Font (Logo, Headlines)
val FrauncesFont = GoogleFont("Fraunces")
val FrauncesFontFamily = FontFamily(
    Font(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Bold),
)

// Outfit — Sans-Serif Body Font (UI, Labels, Inputs)
val OutfitFont = GoogleFont("Outfit")
val OutfitFontFamily = FontFamily(
    Font(googleFont = OutfitFont, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = OutfitFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = OutfitFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = OutfitFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = OutfitFont, fontProvider = provider, weight = FontWeight.Bold),
)

val Typography = Typography(
    // Fraunces für grosse Headlines
    headlineLarge = TextStyle(
        fontFamily = FrauncesFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FrauncesFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.3).sp
    ),
    // Outfit für Titles und Body
    titleLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp
    ),
    labelMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)
