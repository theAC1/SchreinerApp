package com.brunner.lignacalc.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// =================== WARM CRAFT LIGHT ===================
private val WarmCraftLight = lightColorScheme(
    primary = Walnut,
    onPrimary = TextOnDark,
    primaryContainer = Sand,
    onPrimaryContainer = WalnutDeep,
    secondary = CraftGold,
    onSecondary = WalnutDeep,
    secondaryContainer = CraftGoldMuted,
    onSecondaryContainer = WalnutDeep,
    tertiary = GreenOk,
    onTertiary = TextOnDark,
    background = Cream,
    onBackground = TextPrimary,
    surface = Cream,
    onSurface = TextPrimary,
    surfaceVariant = WarmWhite,
    onSurfaceVariant = TextSecondary,
    outline = WalnutPale,
    outlineVariant = SandDark,
    inverseSurface = WalnutDeep,
    inverseOnSurface = TextOnDark,
)

// =================== WARM CRAFT DARK ===================
private val WarmCraftDark = darkColorScheme(
    primary = CraftGoldLight,
    onPrimary = WalnutDeep,
    primaryContainer = Walnut,
    onPrimaryContainer = CraftGoldMuted,
    secondary = CraftGold,
    onSecondary = WalnutDeep,
    secondaryContainer = Walnut,
    onSecondaryContainer = CraftGoldLight,
    tertiary = GreenOk,
    onTertiary = TextOnDark,
    background = DarkBg,
    onBackground = TextOnDark,
    surface = DarkBg,
    onSurface = TextOnDark,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = WalnutPale,
    outline = DarkBorder,
    outlineVariant = DarkBorder,
    inverseSurface = Cream,
    inverseOnSurface = WalnutDeep,
)

@Composable
fun LignaCalcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) WarmCraftDark else WarmCraftLight

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
