package com.brunner.lignacalc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brunner.lignacalc.ui.theme.*

@Composable
fun ResultCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    isGood: Boolean? = null // null = neutral, true = grün, false = rot
) {
    val bgColor = when (isGood) {
        true -> GreenBg
        false -> RedBg
        null -> Sand
    }
    val accentColor = when (isGood) {
        true -> GreenOk
        false -> RedBad
        null -> WalnutPale
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
    ) {
        // Seitenstreifen links
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(4.dp)
                .fillMaxHeight()
                .background(accentColor)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            // Status-Zeile mit Dot
            if (isGood != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                    Text(
                        text = label.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = accentColor,
                        letterSpacing = 0.8.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            } else {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Wert
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = if (isGood != null) 30.sp else 22.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                if (suffix != null) {
                    Text(
                        text = suffix,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}
