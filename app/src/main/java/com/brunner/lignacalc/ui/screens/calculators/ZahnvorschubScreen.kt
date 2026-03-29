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
fun ZahnvorschubScreen(onBack: () -> Unit) {
    var feedRate by remember { mutableStateOf("") }
    var rpm by remember { mutableStateOf("") }
    var teeth by remember { mutableStateOf("") }

    val result = remember(feedRate, rpm, teeth) {
        val v = feedRate.toDoubleOrNull()
        val n = rpm.toDoubleOrNull()
        val z = teeth.toDoubleOrNull()
        if (v != null && n != null && z != null) {
            Calculations.computeZahnvorschub(v, n, z)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_zahnvorschub),
        onBack = onBack,
        infoText = stringResource(R.string.info_zahnvorschub)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = feedRate,
                onValueChange = { feedRate = it },
                label = "V",
                suffix = "m/min",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = teeth,
                onValueChange = { teeth = it },
                label = "Z",
                suffix = stringResource(R.string.teeth),
                modifier = Modifier.weight(1f)
            )
        }

        NumberInputField(
            value = rpm,
            onValueChange = { rpm = it },
            label = "N",
            suffix = "U/min"
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            ResultCard(
                label = "${stringResource(R.string.result)} fz",
                value = result.toString(),
                suffix = "mm"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
