package com.core.presentation.design_system

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CoreBottomBar(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            HorizontalDivider(Modifier.alpha(0.3f))

            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selectedItem == index
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(0.2f),
                        ),
                        selected = isSelected,
                        onClick = {
                            onItemClick(index)
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = null,
                                tint = if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onBackground.copy(0.6f)
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

data class BottomNavigationItem(
    @StringRes val label: Int? = null,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Preview
@Composable
private fun Preview() {
    CoreBottomBar(
        items = listOf(
            BottomNavigationItem(
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavigationItem(
                selectedIcon = Icons.Default.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        ),
        selectedItem = 0,
        onItemClick = {}
    )
}