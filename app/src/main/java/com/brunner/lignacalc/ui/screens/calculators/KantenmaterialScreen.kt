package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.ui.components.CalculatorScaffold
import com.brunner.lignacalc.ui.components.NumberInputField
import com.brunner.lignacalc.ui.components.ResultCard
import com.brunner.lignacalc.util.Calculations

@Composable
fun KantenmaterialScreen(onBack: () -> Unit) {
    var thickness by remember { mutableStateOf("") }
    var outerDiameter by remember { mutableStateOf("") }
    var innerDiameter by remember { mutableStateOf("") }

    val result = remember(thickness, outerDiameter, innerDiameter) {
        val s = thickness.toDoubleOrNull()
        val a = outerDiameter.toDoubleOrNull()
        val b = innerDiameter.toDoubleOrNull()
        if (s != null && a != null && b != null) {
            Calculations.computeKantenmaterialRestlaenge(s, a, b)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_kantenmaterial),
        onBack = onBack,
        infoText = stringResource(R.string.info_kantenmaterial),
        formula = "L = \u03C0 \u00D7 (\u00D8A\u00B2 \u2212 \u00D8b\u00B2) / (4 \u00D7 S \u00D7 1000)"
    ) {

        NumberInputField(
            value = thickness,
            onValueChange = { thickness = it },
            label = "S",
            suffix = "mm"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = outerDiameter,
                onValueChange = { outerDiameter = it },
                label = "\u00D8A",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = innerDiameter,
                onValueChange = { innerDiameter = it },
                label = "\u00D8b",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            ResultCard(
                label = stringResource(R.string.result_restlaenge),
                value = result.toString(),
                suffix = "m"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
