package com.gmail.denuelle42.denuanime.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun FilterDropdown(
    modifier: Modifier = Modifier, label: String,
    type: List<String>,
    shape: Shape = ButtonDefaults.textShape,
    onFilterClick: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        TextButton(
            shape = shape,
            onClick = {
                expanded = true
            },
        ) {
            Icon(imageVector = Icons.Default.FilterAlt, contentDescription = label)
            Text(text = label)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            type.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onFilterClick(index)
                        expanded = false
                    },
                )
            }
        }
    }

}

@Preview
@Composable
private fun FilterTypeDropdownPreview() {
    DenuAnimeTheme {
        FilterDropdown(
            label = "Type",
            type = listOf("TV", "Movie", "Series")
        ) {

        }
    }
}