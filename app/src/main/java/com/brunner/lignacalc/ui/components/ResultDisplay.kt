package com.brunner.lignacalc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.ui.theme.GreenOk
import com.brunner.lignacalc.ui.theme.RedBad

@Composable
fun ResultCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    isGood: Boolean? = null // null = neutral, true = grün, false = rot
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (isGood) {
                true -> GreenOk.copy(alpha = 0.12f)
                false -> RedBad.copy(alpha = 0.12f)
                null -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = when (isGood) {
                        true -> GreenOk
                        false -> RedBad
                        null -> MaterialTheme.colorScheme.onSurface
                    }
                )
                if (suffix != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = suffix,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
