package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.MaterialData
import com.brunner.lignacalc.ui.components.*
import com.brunner.lignacalc.util.Calculations

@Composable
fun PlattenmaterialScreen(onBack: () -> Unit) {
    var selectedMaterial by remember { mutableStateOf(MaterialData.materials.first()) }
    var length by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var thickness by remember { mutableStateOf("") }

    val result = remember(length, width, thickness, selectedMaterial) {
        val l = length.toDoubleOrNull()
        val b = width.toDoubleOrNull()
        val d = thickness.toDoubleOrNull()
        if (l != null && b != null && d != null) {
            Calculations.computePlattenmaterialGewicht(l, b, d, selectedMaterial.weightFactor)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_plattenmaterial),
        onBack = onBack,
        infoText = stringResource(R.string.info_plattenmaterial)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        MaterialDropdown(
            items = MaterialData.materials,
            selectedItem = selectedMaterial,
            onItemSelected = { selectedMaterial = it },
            label = stringResource(R.string.material),
            itemLabel = { it.name }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberInputField(
                value = length,
                onValueChange = { length = it },
                label = "a",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
            NumberInputField(
                value = width,
                onValueChange = { width = it },
                label = "b",
                suffix = "mm",
                modifier = Modifier.weight(1f)
            )
        }

        NumberInputField(
            value = thickness,
            onValueChange = { thickness = it },
            label = "c",
            suffix = "mm"
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            ResultCard(
                label = stringResource(R.string.result_weight),
                value = result.toString(),
                suffix = "kg"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
