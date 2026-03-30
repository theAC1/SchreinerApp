package com.brunner.lignacalc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font as GoogleFontRes
import androidx.compose.ui.unit.sp
import com.brunner.lignacalc.R

// Google Fonts Provider (nur noch für Fraunces)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Fraunces — Serif Display Font (Logo, Headlines)
val FrauncesFont = GoogleFont("Fraunces")
val FrauncesFontFamily = FontFamily(
    GoogleFontRes(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Light),
    GoogleFontRes(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Normal),
    GoogleFontRes(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Medium),
    GoogleFontRes(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.SemiBold),
    GoogleFontRes(googleFont = FrauncesFont, fontProvider = provider, weight = FontWeight.Bold),
)

// Lato — Sans-Serif Body Font (UI, Labels, Inputs) — lokal gebundelt
val LatoFontFamily = FontFamily(
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
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
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp
    ),
    labelMedium = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)
