package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun FilterDropdown(
    modifier: Modifier = Modifier,
    icon : ImageVector = Icons.Default.FilterAlt,
    buttonLabel: String? = null,
    typeLabel : String? = null,
    secondaryTypeLabel : String? = null,
    type: List<String>,
    secondaryType: List<String> = emptyList(),
    shape: Shape = ButtonDefaults.textShape,
    isEnabled : Boolean = true,
    onFilterClick: (String, String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedType by remember { mutableStateOf(type[0]) }
    var selectedSecondaryType by remember { mutableStateOf(if (secondaryType.isNotEmpty()) secondaryType[0] else null) }

    Box(modifier = modifier) {
        TextButton(
            enabled = isEnabled,
            shape = shape,
            onClick = {
                expanded = true
            },
        ) {
            Icon(imageVector = icon, contentDescription = buttonLabel.orEmpty())
            if (buttonLabel != null) {
                Text(text = buttonLabel)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = typeLabel ?: "", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start=6.dp))
                    type.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                selectedType = type[index]
                                onFilterClick(selectedType, selectedSecondaryType)
                                expanded = false
                            },
                            leadingIcon = {
                                if (selectedType == item) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        )
                    }
                }

                if (secondaryType.isNotEmpty()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = secondaryTypeLabel ?: "", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start=6.dp))
                        secondaryType.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    selectedSecondaryType = secondaryType[index]
                                    onFilterClick(selectedType, selectedSecondaryType)
                                    expanded = false
                                },
                                leadingIcon = {
                                    if (selectedSecondaryType == item) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Gray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }

                }
            }

        }
    }

}

@Preview
@Composable
private fun FilterTypeDropdownPreview() {
    DenuAnimeTheme {
        FilterDropdown(
            buttonLabel = "Type",
            typeLabel = "Type",
            secondaryTypeLabel = "Type",
            type = listOf("TV", "Movie", "Series"),
            secondaryType = listOf("TV", "Movie", "Series")
        ) { type, secondaryType ->

        }
    }
}