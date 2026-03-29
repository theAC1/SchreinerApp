package com.brunner.lignacalc.ui.screens.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R
import com.brunner.lignacalc.data.CuttingSpeedData
import com.brunner.lignacalc.data.CuttingSpeedMaterial
import com.brunner.lignacalc.ui.components.*
import com.brunner.lignacalc.util.Calculations

enum class MachineType { SAEGEN, FRAESEN }

@Composable
fun SchnittgeschwindigkeitScreen(onBack: () -> Unit) {
    var selectedMaterial by remember { mutableStateOf(CuttingSpeedData.materials.first()) }
    var machineType by remember { mutableStateOf(MachineType.SAEGEN) }
    var diameter by remember { mutableStateOf("") }
    var rpm by remember { mutableStateOf("") }

    val result = remember(diameter, rpm) {
        val d = diameter.toDoubleOrNull()
        val n = rpm.toDoubleOrNull()
        if (d != null && n != null) {
            Calculations.computeSchnittgeschwindigkeit(d, n)
        } else null
    }

    val isInRange = remember(result, selectedMaterial, machineType) {
        if (result != null) {
            val (min, max) = when (machineType) {
                MachineType.SAEGEN -> selectedMaterial.saegenMin to selectedMaterial.saegenMax
                MachineType.FRAESEN -> selectedMaterial.fraesenMin to selectedMaterial.fraesenMax
            }
            Calculations.isInRange(result, min, max)
        } else null
    }

    CalculatorScaffold(
        title = stringResource(R.string.tool_schnittgeschwindigkeit),
        onBack = onBack,
        infoText = stringResource(R.string.info_schnittgeschwindigkeit)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        MaterialDropdown(
            items = CuttingSpeedData.materials,
            selectedItem = selectedMaterial,
            onItemSelected = { selectedMaterial = it },
            label = stringResource(R.string.material),
            itemLabel = { it.nameDe }
        )

        // Sägen / Fräsen Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = machineType == MachineType.SAEGEN,
                onClick = { machineType = MachineType.SAEGEN },
                label = { Text(stringResource(R.string.saegen)) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = machineType == MachineType.FRAESEN,
                onClick = { machineType = MachineType.FRAESEN },
                label = { Text(stringResource(R.string.fraesen)) },
                modifier = Modifier.weight(1f)
            )
        }

        NumberInputField(
            value = diameter,
            onValueChange = { diameter = it },
            label = stringResource(R.string.tool_diameter),
            suffix = "mm"
        )

        NumberInputField(
            value = rpm,
            onValueChange = { rpm = it },
            label = stringResource(R.string.rpm),
            suffix = "U/min"
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (result != null) {
            ResultCard(
                label = stringResource(R.string.result_vc),
                value = result.toString(),
                suffix = "m/s",
                isGood = isInRange
            )

            // Bereich anzeigen
            val (min, max) = when (machineType) {
                MachineType.SAEGEN -> selectedMaterial.saegenMin to selectedMaterial.saegenMax
                MachineType.FRAESEN -> selectedMaterial.fraesenMin to selectedMaterial.fraesenMax
            }
            Text(
                text = "${stringResource(R.string.recommended_range)}: $min – $max m/s",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
