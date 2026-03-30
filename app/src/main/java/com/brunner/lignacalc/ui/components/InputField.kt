package com.brunner.lignacalc.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumberInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    enabled: Boolean = true,
    isResult: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Nur Zahlen, Punkt und Minus erlauben
            val filtered = newValue.filter { it.isDigit() || it == '.' || it == '-' }
            onValueChange(filtered)
        },
        label = { Text(label) },
        suffix = if (suffix != null) { { Text(suffix) } } else null,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        enabled = enabled,
        readOnly = isResult,
        colors = if (isResult) {
            OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.primary,
            )
        } else {
            OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        }
    )
}
