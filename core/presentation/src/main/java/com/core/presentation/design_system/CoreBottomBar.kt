package com.core.presentation.design_system

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.presentation.R


@Composable
fun CoreBottomBar(
    hasInternet: Boolean = true,
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
            if (!hasInternet) {
                Text(
                    text = stringResource(R.string.no_internet_connection),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.error)
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                )
            } else {
                HorizontalDivider(Modifier.alpha(0.3f))
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selectedItem == index
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(0.1f),
                        ),
                        selected = isSelected,
                        onClick = {
                            onItemClick(index)
                        },
                        icon = {
                            Icon(
                                painter = if (isSelected) {
                                    painterResource(item.selectedIcon)
                                } else {
                                    painterResource(item.unselectedIcon)
                                },
                                contentDescription = null,
                                tint = if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onBackground.copy(0.6f)
                                },
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
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
)
