package com.brunner.lignacalc.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brunner.lignacalc.R

data class LanguageOption(
    val code: String,
    val displayName: String,
    val flag: String
)

val availableLanguages = listOf(
    LanguageOption("de", "Deutsch", "\uD83C\uDDE8\uD83C\uDDED"),
    LanguageOption("en", "English", "\uD83C\uDDEC\uD83C\uDDE7"),
    LanguageOption("fr", "Fran\u00E7ais", "\uD83C\uDDE8\uD83C\uDDED"),
    LanguageOption("it", "Italiano", "\uD83C\uDDE8\uD83C\uDDED"),
)

@Composable
fun LanguageDialog(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.select_language)) },
        text = {
            Column {
                availableLanguages.forEach { lang ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(lang.code) }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = lang.flag,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = lang.displayName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        if (lang.code == currentLanguage) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
