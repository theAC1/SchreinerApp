package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.WoodData
import com.brunner.lignacalc.ui.components.*
import com.brunner.lignacalc.util.Calculations

@Composable
fun HolzschwundScreen(onBack: () -> Unit) {
    var selectedWood by remember { mutableStateOf(WoodData.types.first()) }
    var moisture by remember { mutableStateOf("") }
    var lengthA by remember { mutableStateOf("") }
    var lengthB by remember { mutableStateOf("") }

    val moistureVal = moisture.toDoubleOrNull()
    val lengthAVal = lengthA.toDoubleOrNull()
    val lengthBVal = lengthB.toDoubleOrNull()

    val resultA = remember(lengthA, moisture, selectedWood) {
        if (lengthAVal != null && moistureVal != null) {
            Calculations.computeHolzschwund(lengthAVal, selectedWood.radial, moistureVal)
        } else null
    }

    val resultB = remember(lengthB, moisture, selectedWood) {
        if (lengthBVal != null && moistureVal != null) {
            Calculations.computeHolzschwund(lengthBVal, selectedWood.tangential, moistureVal)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_holzschwund),
        onBack = onBack,
        infoText = stringResource(R.string.info_holzschwund),
        formula = "S = L \u00D7 (Koeffizient \u00D7 \u0394 Feuchte%)"
    ) {

        MaterialDropdown(
            items = WoodData.types,
            selectedItem = selectedWood,
            onItemSelected = { selectedWood = it },
            label = stringResource(R.string.wood_type),
            itemLabel = { it.name }
        )

        NumberInputField(
            value = moisture,
            onValueChange = { moisture = it },
            label = stringResource(R.string.moisture_change),
            suffix = "%"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = lengthA,
                onValueChange = { lengthA = it },
                label = "${stringResource(R.string.dimension)} a (${stringResource(R.string.radial)})",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = lengthB,
                onValueChange = { lengthB = it },
                label = "${stringResource(R.string.dimension)} b (${stringResource(R.string.tangential)})",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (resultA != null) {
                ResultCard(
                    label = "${stringResource(R.string.result)} a",
                    value = resultA.toString(),
                    suffix = "mm",
                    modifier = Modifier.weight(1f)
                )
            }
            if (resultB != null) {
                ResultCard(
                    label = "${stringResource(R.string.result)} b",
                    value = resultB.toString(),
                    suffix = "mm",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
