package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.UnitConversionData
import com.brunner.lignacalc.data.UnitDef
import com.brunner.lignacalc.ui.components.*
import com.brunner.lignacalc.util.Calculations

@Composable
fun EinheitenrechnerScreen(onBack: () -> Unit) {
    var selectedCategory by remember { mutableStateOf(UnitConversionData.Category.LENGTH) }
    var sourceValue by remember { mutableStateOf("") }

    val units = remember(selectedCategory) {
        UnitConversionData.getUnits(selectedCategory)
    }
    var selectedUnit by remember(selectedCategory) { mutableStateOf(units.first()) }

    val results = remember(sourceValue, selectedUnit, selectedCategory) {
        val v = sourceValue.toDoubleOrNull()
        if (v != null) {
            UnitConversionData.convertAll(selectedCategory, selectedUnit.id, v)
        } else emptyList()
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_einheitenrechner),
        onBack = onBack
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Längen / Flächen Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedCategory == UnitConversionData.Category.LENGTH,
                onClick = {
                    selectedCategory = UnitConversionData.Category.LENGTH
                    sourceValue = ""
                },
                label = { Text(stringResource(R.string.lengths)) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedCategory == UnitConversionData.Category.AREA,
                onClick = {
                    selectedCategory = UnitConversionData.Category.AREA
                    sourceValue = ""
                },
                label = { Text(stringResource(R.string.areas)) },
                modifier = Modifier.weight(1f)
            )
        }

        MaterialDropdown(
            items = units,
            selectedItem = selectedUnit,
            onItemSelected = { selectedUnit = it },
            label = stringResource(R.string.source_unit),
            itemLabel = { it.displayName }
        )

        NumberInputField(
            value = sourceValue,
            onValueChange = { sourceValue = it },
            label = selectedUnit.displayName
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ergebnis-Liste
        results.forEach { (unit, value) ->
            ResultCard(
                label = unit.displayName,
                value = value.toString()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
