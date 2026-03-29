package com.brunner.lignacalc.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.ui.navigation.Screen

data class CalculatorTool(
    val titleRes: Int,
    val subtitleRes: Int,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCalculator: (String) -> Unit
) {
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

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("LignaCalc")
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(tools) { tool ->
                ToolCard(
                    tool = tool,
                    onClick = { onNavigateToCalculator(tool.route) }
                )
            }
        }
    }
}

@Composable
private fun ToolCard(
    tool: CalculatorTool,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = tool.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(tool.titleRes),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(tool.subtitleRes),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
