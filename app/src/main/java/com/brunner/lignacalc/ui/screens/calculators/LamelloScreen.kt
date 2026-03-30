package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.*
import com.brunner.lignacalc.ui.components.CalculatorScaffold
import com.brunner.lignacalc.ui.components.NumberInputField
import com.brunner.lignacalc.ui.theme.*
import com.brunner.lignacalc.util.Calculations

@Composable
fun LamelloScreen(onBack: () -> Unit) {
    var selectedSituation by remember { mutableStateOf(JoiningSituation.G) }
    var angle by remember { mutableStateOf("90") }
    var material1 by remember { mutableStateOf("19") }
    var material2 by remember { mutableStateOf("19") }
    var selectedConnectorIndex by remember { mutableStateOf(0) }
    var selectedTechIndex by remember { mutableStateOf(0) }

    // Aktueller Connector + Technologie
    val currentConnector = LamelloData.connectors.getOrNull(selectedConnectorIndex)
    val currentProfile = currentConnector?.profiles?.getOrNull(selectedTechIndex)

    // Berechnung für ausgewählten Connector (situationsabhängig)
    val currentResult = remember(angle, material1, material2, selectedConnectorIndex, selectedTechIndex, selectedSituation) {
        val a = angle.toDoubleOrNull() ?: return@remember null
        val m1 = material1.toDoubleOrNull() ?: return@remember null
        val m2 = material2.toDoubleOrNull() ?: return@remember null
        val profile = currentConnector?.profiles?.getOrNull(selectedTechIndex) ?: return@remember null
        Calculations.computeLamello(m1, m2, a, profile.slotOffset, selectedSituation.code, profile.tolerance)
    }

    // Alle Ergebnisse für die Tabelle
    val allResults = remember(angle, material1, material2, selectedSituation) {
        val a = angle.toDoubleOrNull() ?: return@remember emptyMap()
        val m1 = material1.toDoubleOrNull() ?: return@remember emptyMap()
        val m2 = material2.toDoubleOrNull() ?: return@remember emptyMap()

        val results = mutableMapOf<Pair<Int, Int>, Calculations.LamelloResult>()
        LamelloData.connectors.forEachIndexed { ci, connector ->
            connector.profiles.forEachIndexed { ti, profile ->
                val result = Calculations.computeLamello(m1, m2, a, profile.slotOffset, selectedSituation.code, profile.tolerance)
                if (result != null) {
                    results[ci to ti] = result
                }
            }
        }
        results
    }

    // Bild-URL für den Lamello-Server
    val imageUrl = remember(selectedSituation, angle, material1, material2, selectedConnectorIndex, selectedTechIndex) {
        val a = angle.toDoubleOrNull() ?: return@remember null
        val m1 = material1.toDoubleOrNull() ?: return@remember null
        val m2 = material2.toDoubleOrNull() ?: return@remember null
        val connector = currentConnector ?: return@remember null
        val profile = currentProfile ?: return@remember null

        LamelloData.buildImageUrl(
            m1 = m1, m2 = m2, angle = a,
            situationCode = selectedSituation.code,
            technologyCode = profile.technology.urlCode,
            connectorCode = connector.connectorCode
        )
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_lamello),
        onBack = onBack,
        infoText = stringResource(R.string.info_lamello)
    ) {
        // ===== VERBINDUNGSSITUATION =====
        Text(
            text = stringResource(R.string.lamello_situation),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JoiningSituation.entries.forEach { situation ->
                SituationChip(
                    label = when (situation) {
                        JoiningSituation.S -> stringResource(R.string.lamello_situation_s)
                        JoiningSituation.G -> stringResource(R.string.lamello_situation_g)
                        JoiningSituation.M -> stringResource(R.string.lamello_situation_m)
                        JoiningSituation.T -> stringResource(R.string.lamello_situation_t)
                    },
                    selected = situation == selectedSituation,
                    onClick = { selectedSituation = situation },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // ===== EINGABEFELDER =====
        NumberInputField(
            value = angle,
            onValueChange = { angle = it },
            label = stringResource(R.string.lamello_angle),
            suffix = "°"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = material1,
                onValueChange = { material1 = it },
                label = "M1",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = material2,
                onValueChange = { material2 = it },
                label = "M2",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ===== TECHNISCHE ZEICHNUNG (Lamello Server) =====
        if (imageUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmWhite)
                    .border(0.5.dp, SandDark, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Lamello Konstruktionsdetail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // ===== Y-WERTE =====
        if (currentResult != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                YValueChip("Y1", currentResult.y1)
                YValueChip("Y2", currentResult.y2)
                YValueChip("Y3", currentResult.y3)
                YValueChip("Y4", currentResult.y4)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===== CONNECTOR-TABELLE =====
        if (allResults.isNotEmpty()) {
            Text(
                text = stringResource(R.string.lamello_processing),
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            ConnectorTable(
                allResults = allResults,
                selectedConnectorIndex = selectedConnectorIndex,
                selectedTechIndex = selectedTechIndex,
                onSelect = { ci, ti ->
                    selectedConnectorIndex = ci
                    selectedTechIndex = ti
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.lamello_disclaimer),
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SituationChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) Walnut else Sand)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) TextOnDark else TextPrimary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun YValueChip(label: String, value: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GreenBg)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = GreenOk,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary
        )
    }
}

@Composable
private fun ConnectorTable(
    allResults: Map<Pair<Int, Int>, Calculations.LamelloResult>,
    selectedConnectorIndex: Int,
    selectedTechIndex: Int,
    onSelect: (Int, Int) -> Unit
) {
    val connectors = LamelloData.connectors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(WarmWhite)
    ) {
        // === Header Zeile 1: Technologie-Namen ===
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Sand)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = stringResource(R.string.lamello_connector),
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                modifier = Modifier.weight(1.4f)
            )
            Text(
                text = "CNC",
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Zeta",
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.7f)
            )
            Text(
                text = "+2mm",
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.7f)
            )
            Text(
                text = "+4mm",
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.7f)
            )
        }

        // === Header Zeile 2: X1 / X2 Beschriftungen ===
        HorizontalDivider(thickness = 0.5.dp, color = SandDark)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Sand.copy(alpha = 0.5f))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Spacer(modifier = Modifier.weight(1.4f))
            // CNC: X1 X2
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("X1", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 9.sp, textAlign = TextAlign.Center)
                Text("X2", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 9.sp, textAlign = TextAlign.Center)
            }
            // Zeta: X1 X2
            Row(modifier = Modifier.weight(0.7f), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("X1", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
                Text("X2", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
            }
            // +2mm: X1 X2
            Row(modifier = Modifier.weight(0.7f), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("X1", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
                Text("X2", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
            }
            // +4mm: X1 X2
            Row(modifier = Modifier.weight(0.7f), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("X1", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
                Text("X2", style = MaterialTheme.typography.labelMedium, color = TextMuted, fontSize = 8.sp, textAlign = TextAlign.Center)
            }
        }

        // === Daten-Zeilen ===
        connectors.forEachIndexed { ci, connector ->
            HorizontalDivider(thickness = 0.5.dp, color = SandDark)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Connector-Name
                Text(
                    text = connector.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1.4f),
                    fontSize = 10.sp,
                    lineHeight = 13.sp
                )

                LamelloData.allTechnologies.forEach { tech ->
                    val ti = connector.profiles.indexOfFirst { it.technology == tech }
                    val result = if (ti >= 0) allResults[ci to ti] else null
                    val isSelected = ci == selectedConnectorIndex && ti == selectedTechIndex && ti >= 0
                    val weight = if (tech == LamelloTechnology.CNC) 1f else 0.7f

                    Box(
                        modifier = Modifier
                            .weight(weight)
                            .clip(RoundedCornerShape(6.dp))
                            .then(
                                if (result != null) {
                                    Modifier
                                        .background(if (isSelected) Walnut else Sand.copy(alpha = 0.3f))
                                        .clickable { onSelect(ci, ti) }
                                } else Modifier
                            )
                            .padding(vertical = 4.dp, horizontal = 1.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (result != null) {
                            // X1 und X2 nebeneinander wie im Original
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "${result.x1}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isSelected) TextOnDark else TextPrimary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "${result.x2}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isSelected) TextOnDark.copy(alpha = 0.8f) else TextSecondary,
                                    fontSize = 9.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Text(
                                text = "-",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
