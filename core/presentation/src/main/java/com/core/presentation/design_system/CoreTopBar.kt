package com.core.presentation.design_system

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.theme.theme.CoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreTopBar(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    titleFontWeight: FontWeight = FontWeight.Medium,
    titleContent: (@Composable () -> Unit)? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContent: (@Composable () -> Unit)? = null,
    navigationIconDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContent: (@Composable () -> Unit)? = null,
    actionIconDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    containerColor: Color = MaterialTheme.colorScheme.background,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.background
) {

    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        ),
        title = {
            if (titleText != null) {
                Text(
                    text = titleText,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = titleFontWeight
                    )
                )
            } else if (titleContent != null) {
                titleContent()
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconDescription,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            } else if (navigationIconContent != null) {
                navigationIconContent()
            }
        },
        actions = {
            Row {
                if (actionIcon != null) {
                    IconButton(onClick = onActionClick) {
                        Icon(
                            imageVector = actionIcon,
                            contentDescription = actionIconDescription,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else if (actionIconContent != null) {
                    actionIconContent()
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreMediumTopBar(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    titleFontWeight: FontWeight = FontWeight.Medium,
    titleContent: (@Composable () -> Unit)? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContent: (@Composable () -> Unit)? = null,
    navigationIconDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContent: (@Composable () -> Unit)? = null,
    actionIconDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    containerColor: Color = MaterialTheme.colorScheme.background,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.background
) {

    MediumTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        ),
        title = {
            if (titleText != null) {
                Text(
                    text = titleText,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = titleFontWeight
                    )
                )
            } else if (titleContent != null) {
                titleContent()
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconDescription,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            } else if (navigationIconContent != null) {
                navigationIconContent()
            }
        },
        actions = {
            Row {
                if (actionIcon != null) {
                    IconButton(onClick = onActionClick) {
                        Icon(
                            imageVector = actionIcon,
                            contentDescription = actionIconDescription,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else if (actionIconContent != null) {
                    actionIconContent()
                }
                Spacer(Modifier.width(16.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreLargeTopBar(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    titleFontWeight: FontWeight = FontWeight.Medium,
    titleContent: (@Composable () -> Unit)? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContent: (@Composable () -> Unit)? = null,
    navigationIconDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContent: (@Composable () -> Unit)? = null,
    actionIconDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    containerColor: Color = MaterialTheme.colorScheme.background,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.background
) {

    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        ),
        title = {
            if (titleText != null) {
                Text(
                    text = titleText,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = titleFontWeight
                    )
                )
            } else if (titleContent != null) {
                titleContent()
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconDescription,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            } else if (navigationIconContent != null) {
                navigationIconContent()
            }
        },
        actions = {
            Row {
                if (actionIcon != null) {
                    IconButton(onClick = onActionClick) {
                        Icon(
                            imageVector = actionIcon,
                            contentDescription = actionIconDescription,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else if (actionIconContent != null) {
                    actionIconContent()
                }
                Spacer(Modifier.width(16.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    CoreTheme {
        CoreTopBar(
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = Icons.Outlined.ArrowBackIosNew,
            actionIcon = Icons.Outlined.Search,
            titleText = "Core",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MediumTopBarPreview() {
    CoreTheme {
        CoreMediumTopBar(
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = Icons.Outlined.ArrowBackIosNew,
            actionIcon = Icons.Outlined.Search,
            titleText = "Core",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun LargeTopBarPreview() {
    CoreTheme {
        CoreLargeTopBar(
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = Icons.Outlined.ArrowBackIosNew,
            actionIcon = Icons.Outlined.Search,
            titleText = "Core",
        )
    }
}