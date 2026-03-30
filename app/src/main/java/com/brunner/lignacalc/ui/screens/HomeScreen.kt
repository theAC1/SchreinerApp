package com.brunner.lignacalc.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.FavoritesRepository
import com.brunner.lignacalc.ui.navigation.Screen
import com.brunner.lignacalc.ui.theme.*
import kotlinx.coroutines.launch

data class CalculatorTool(
    val titleRes: Int,
    val subtitleRes: Int,
    val icon: ImageVector? = null,
    val iconRes: Int? = null,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCalculator: (String) -> Unit,
    onOpenSettings: () -> Unit = {}
) {
    val context = LocalContext.current
    val favoritesRepo = remember { FavoritesRepository(context) }
    val favorites by favoritesRepo.favorites.collectAsState(initial = emptySet())
    val scope = rememberCoroutineScope()

    val tools = listOf(
        CalculatorTool(R.string.tool_schnittgeschwindigkeit, R.string.tool_schnittgeschwindigkeit_sub, iconRes = R.drawable.ic_schnittgeschwindigkeit, route = Screen.Schnittgeschwindigkeit.route),
        CalculatorTool(R.string.tool_gehrung, R.string.tool_gehrung_sub, icon = Icons.Default.Flag, route = Screen.Gehrung.route),
        CalculatorTool(R.string.tool_zahnvorschub, R.string.tool_zahnvorschub_sub, iconRes = R.drawable.ic_zahnschnitt, route = Screen.Zahnvorschub.route),
        CalculatorTool(R.string.tool_kantenmaterial, R.string.tool_kantenmaterial_sub, icon = Icons.Default.Straighten, route = Screen.Kantenmaterial.route),
        CalculatorTool(R.string.tool_plattenmaterial, R.string.tool_plattenmaterial_sub, icon = Icons.Default.FitnessCenter, route = Screen.Plattenmaterial.route),
        CalculatorTool(R.string.tool_goldener_schnitt, R.string.tool_goldener_schnitt_sub, iconRes = R.drawable.ic_goldener_schnitt, route = Screen.GoldenerSchnitt.route),
        CalculatorTool(R.string.tool_holzschwund, R.string.tool_holzschwund_sub, icon = Icons.Default.Park, route = Screen.Holzschwund.route),
        CalculatorTool(R.string.tool_einheitenrechner, R.string.tool_einheitenrechner_sub, icon = Icons.Default.SwapHoriz, route = Screen.Einheitenrechner.route),
        CalculatorTool(R.string.tool_lamello, R.string.tool_lamello_sub, icon = Icons.Default.Handyman, route = Screen.Lamello.route),
    )

    Scaffold(
        containerColor = Cream
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ===== HEADER =====
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WalnutDeep)
                        .padding(start = 22.dp, end = 14.dp, top = 48.dp, bottom = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.all_tools),
                            style = MaterialTheme.typography.headlineLarge,
                            color = TextOnDark
                        )
                        IconButton(onClick = onOpenSettings) {
                            Icon(
                                Icons.Default.Language,
                                contentDescription = stringResource(R.string.language),
                                tint = TextOnDark.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // ===== RECHNER LISTE (Favoriten zuerst) =====
            val sortedTools = tools.sortedByDescending { it.route in favorites }

            items(sortedTools) { tool ->
                ToolListItem(
                    tool = tool,
                    isFavorite = tool.route in favorites,
                    onClick = { onNavigateToCalculator(tool.route) },
                    onToggleFavorite = { scope.launch { favoritesRepo.toggleFavorite(tool.route) } }
                )
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = SandDark
                )
            }
        }
    }
}

@Composable
private fun ToolListItem(
    tool: CalculatorTool,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(WarmWhite)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon links
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Sand),
            contentAlignment = Alignment.Center
        ) {
            if (tool.iconRes != null) {
                // Custom PNG Icon
                Image(
                    painter = painterResource(id = tool.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    colorFilter = ColorFilter.tint(Walnut)
                )
            } else if (tool.icon != null) {
                // Material Icon
                Icon(
                    imageVector = tool.icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Walnut
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Titel
        Text(
            text = stringResource(tool.titleRes),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )

        // Favoriten-Stern
        IconButton(
            onClick = onToggleFavorite,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = null,
                tint = if (isFavorite) CraftGold else SandDark,
                modifier = Modifier.size(18.dp)
            )
        }

        // Chevron rechts
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(24.dp)
        )
    }
}
