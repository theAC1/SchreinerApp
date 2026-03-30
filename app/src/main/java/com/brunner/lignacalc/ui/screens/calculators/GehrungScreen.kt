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
fun GehrungScreen(onBack: () -> Unit) {
    var gamma by remember { mutableStateOf("") }
    var sideA by remember { mutableStateOf("") }
    var sideB by remember { mutableStateOf("") }

    val result = remember(gamma, sideA, sideB) {
        val g = gamma.toDoubleOrNull()
        val a = sideA.toDoubleOrNull()
        val b = sideB.toDoubleOrNull()
        if (g != null && a != null && b != null) {
            Calculations.computeGehrung(g, a, b)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_gehrung),
        onBack = onBack,
        infoText = stringResource(R.string.info_gehrung),
        formula = "d = \u221A(x\u00B2 + y\u00B2 \u2212 2xy\u00B7cos(180\u2212\u03B3))",
        drawingRes = R.drawable.drawing_gehrung
    ) {

        NumberInputField(
            value = gamma,
            onValueChange = { gamma = it },
            label = "${stringResource(R.string.angle)} \u03B3",
            suffix = "°"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = sideA,
                onValueChange = { sideA = it },
                label = "A",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = sideB,
                onValueChange = { sideB = it },
                label = "B",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ResultCard(
                    label = "${stringResource(R.string.result)} \u03B1",
                    value = result.alpha.toString(),
                    suffix = "°",
                    modifier = Modifier.weight(1f)
                )
                ResultCard(
                    label = "${stringResource(R.string.result)} \u03B2",
                    value = result.beta.toString(),
                    suffix = "°",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
