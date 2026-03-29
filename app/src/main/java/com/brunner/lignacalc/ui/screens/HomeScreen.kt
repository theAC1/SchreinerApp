package com.brunner.lignacalc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.FavoritesRepository
import com.brunner.lignacalc.ui.navigation.Screen
import com.brunner.lignacalc.ui.theme.*
import kotlinx.coroutines.launch

data class CalculatorTool(
    val titleRes: Int,
    val subtitleRes: Int,
    val icon: ImageVector,
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
        CalculatorTool(R.string.tool_schnittgeschwindigkeit, R.string.tool_schnittgeschwindigkeit_sub, Icons.Default.Speed, Screen.Schnittgeschwindigkeit.route),
        CalculatorTool(R.string.tool_gehrung, R.string.tool_gehrung_sub, Icons.Default.Architecture, Screen.Gehrung.route),
        CalculatorTool(R.string.tool_zahnvorschub, R.string.tool_zahnvorschub_sub, Icons.Default.Settings, Screen.Zahnvorschub.route),
        CalculatorTool(R.string.tool_kantenmaterial, R.string.tool_kantenmaterial_sub, Icons.Default.Straighten, Screen.Kantenmaterial.route),
        CalculatorTool(R.string.tool_plattenmaterial, R.string.tool_plattenmaterial_sub, Icons.Default.Scale, Screen.Plattenmaterial.route),
        CalculatorTool(R.string.tool_goldener_schnitt, R.string.tool_goldener_schnitt_sub, Icons.Default.AutoAwesome, Screen.GoldenerSchnitt.route),
        CalculatorTool(R.string.tool_holzschwund, R.string.tool_holzschwund_sub, Icons.Default.Forest, Screen.Holzschwund.route),
        CalculatorTool(R.string.tool_einheitenrechner, R.string.tool_einheitenrechner_sub, Icons.Default.SwapHoriz, Screen.Einheitenrechner.route),
    )

    val favoriteTools = tools.filter { it.route in favorites }

    Scaffold(
        containerColor = Cream
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ===== HEADER =====
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp, end = 14.dp, top = 16.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LignaCalc",
                            style = MaterialTheme.typography.headlineLarge,
                            color = WalnutDeep
                        )
                        Text(
                            text = stringResource(R.string.app_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    }
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            Icons.Default.Language,
                            contentDescription = stringResource(R.string.language),
                            tint = WalnutPale
                        )
                    }
                }
            }

            // ===== FAVORITEN HORIZONTAL =====
            if (favoriteTools.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    SectionLabel(
                        text = "\u2605 ${stringResource(R.string.favorites)}",
                        color = CraftGold
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 22.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        favoriteTools.forEach { tool ->
                            FavoriteCard(
                                tool = tool,
                                onClick = { onNavigateToCalculator(tool.route) }
                            )
                        }
                    }
                }

                // Divider
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp, vertical = 4.dp)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(SandDark, Cream)
                                )
                            )
                    )
                }
            }

            // ===== ALLE RECHNER =====
            item(span = { GridItemSpan(2) }) {
                SectionLabel(
                    text = stringResource(R.string.all_tools),
                    color = TextMuted
                )
            }

            items(tools) { tool ->
                ToolCard(
                    tool = tool,
                    isFavorite = tool.route in favorites,
                    onClick = { onNavigateToCalculator(tool.route) },
                    onToggleFavorite = { scope.launch { favoritesRepo.toggleFavorite(tool.route) } },
                    modifier = Modifier.padding(
                        start = if (tools.indexOf(tool) % 2 == 0) 22.dp else 0.dp,
                        end = if (tools.indexOf(tool) % 2 == 1) 22.dp else 0.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String, color: androidx.compose.ui.graphics.Color) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = color,
        modifier = Modifier.padding(start = 22.dp, top = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun FavoriteCard(
    tool: CalculatorTool,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        WalnutDeep.copy(alpha = 0.9f),
                        WalnutDeep
                    )
                )
            )
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CraftGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tool.icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = CraftGold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(tool.titleRes),
                style = MaterialTheme.typography.labelMedium,
                color = TextOnDark.copy(alpha = 0.85f),
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun ToolCard(
    tool: CalculatorTool,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarmWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Stern oben rechts
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(34.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                    contentDescription = null,
                    tint = if (isFavorite) CraftGold else SandDark,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon-Box
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Sand),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tool.icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Walnut
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(tool.titleRes),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(tool.subtitleRes),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = TextMuted
                )
            }
        }
    }
}
