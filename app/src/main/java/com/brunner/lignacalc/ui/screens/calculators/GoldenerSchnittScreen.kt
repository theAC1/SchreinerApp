package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.ui.components.CalculatorScaffold
import com.brunner.lignacalc.ui.components.NumberInputField
import com.brunner.lignacalc.ui.components.ResultCard
import com.brunner.lignacalc.util.Calculations

enum class GoldenInput { MAJOR, MINOR, TOTAL }

@Composable
fun GoldenerSchnittScreen(onBack: () -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    var selectedInput by remember { mutableStateOf(GoldenInput.MAJOR) }

    val result = remember(inputValue, selectedInput) {
        val v = inputValue.toDoubleOrNull()
        if (v != null) {
            when (selectedInput) {
                GoldenInput.MAJOR -> Calculations.goldenRatioFromMajor(v)
                GoldenInput.MINOR -> Calculations.goldenRatioFromMinor(v)
                GoldenInput.TOTAL -> Calculations.goldenRatioFromTotal(v)
            }
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_goldener_schnitt),
        onBack = onBack,
        infoText = stringResource(R.string.info_goldener_schnitt),
        formula = "(a + b) : a = 1.618",
        drawingRes = R.drawable.drawing_goldener_schnitt
    ) {

        // Input-Typ Auswahl
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedInput == GoldenInput.MAJOR,
                onClick = { selectedInput = GoldenInput.MAJOR; inputValue = "" },
                label = { Text("Major (M)") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedInput == GoldenInput.MINOR,
                onClick = { selectedInput = GoldenInput.MINOR; inputValue = "" },
                label = { Text("Minor (m)") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedInput == GoldenInput.TOTAL,
                onClick = { selectedInput = GoldenInput.TOTAL; inputValue = "" },
                label = { Text("Total (a)") },
                modifier = Modifier.weight(1f)
            )
        }

        NumberInputField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = when (selectedInput) {
                GoldenInput.MAJOR -> "${stringResource(R.string.length)} M"
                GoldenInput.MINOR -> "${stringResource(R.string.length)} m"
                GoldenInput.TOTAL -> "${stringResource(R.string.length)} a"
            },
            suffix = "mm"
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            if (selectedInput != GoldenInput.MAJOR) {
                ResultCard(label = "Major (M)", value = result.major.toString(), suffix = "mm")
            }
            if (selectedInput != GoldenInput.MINOR) {
                ResultCard(label = "Minor (m)", value = result.minor.toString(), suffix = "mm")
            }
            if (selectedInput != GoldenInput.TOTAL) {
                ResultCard(label = "Total (a)", value = result.total.toString(), suffix = "mm")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
