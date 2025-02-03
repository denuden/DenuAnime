package com.gmail.denuelle42.denuanime.ui.common.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun GenreFilterChip(
    modifier: Modifier = Modifier,
    categoryList: List<Genre>,
    isEnabled: Boolean = true,
    onSelectedCategory: (Genre) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categoryList) { category ->
            FilterChip(
                enabled = isEnabled,
                onClick = {
                    onSelectedCategory(category)
                },
                label = {
                    Text(category.name ?: "Unknown")
                },
                selected = category.isSelected,
                leadingIcon = if (category.isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(R.string.selected),
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Preview
@Composable
private fun CategoriesFilterChipPreview() {
    DenuAnimeTheme {
        GenreFilterChip(
            categoryList = listOf(
                Genre(name = "wegwe"),
                Genre(name = "wegwe"),
                Genre(name = "wegwe"),
            )
        ) { }
    }
}