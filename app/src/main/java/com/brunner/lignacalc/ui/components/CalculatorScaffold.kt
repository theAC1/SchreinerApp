package com.brunner.lignacalc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.ui.theme.*

@Composable
fun CalculatorScaffold(
    title: String,
    onBack: () -> Unit,
    infoText: String? = null,
    formula: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    var showInfo by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // ===== DARK HEADER =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(WalnutDeep)
                .statusBarsPadding()
                .padding(bottom = 20.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                // Back + Info Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(TextOnDark.copy(alpha = 0.06f))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            tint = TextOnDark.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    if (infoText != null) {
                        IconButton(
                            onClick = { showInfo = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(TextOnDark.copy(alpha = 0.06f))
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Info",
                                tint = TextOnDark.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextOnDark
                )

                // Formula
                if (formula != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formula,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOnDark.copy(alpha = 0.4f)
                    )
                }
            }
        }

        // ===== BODY =====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            content()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Info Dialog
    if (showInfo && infoText != null) {
        AlertDialog(
            onDismissRequest = { showInfo = false },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = WalnutDeep
                )
            },
            text = {
                Text(
                    text = infoText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(onClick = { showInfo = false }) {
                    Text("OK", color = CraftGold)
                }
            },
            containerColor = WarmWhite
        )
    }
}
