package org.dreamerslab.newslayer.feature.onboarding

import org.dreamerslab.newslayer.common.resources.R as CommonResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.dreamerslab.newslayer.core.model.DarkThemeConfig
import org.dreamerslab.newslayer.core.model.FollowableCategory
import org.dreamerslab.newslayer.ui.components.PrimaryButton
import org.dreamerslab.newslayer.ui.components.SelectableChip
import org.dreamerslab.newslayer.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    onNavigateUp: () -> Unit,
    onContinueClick: () -> Unit,
    viewModel: ConfigScreenViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.feature_onboarding_configure_screen_title))
                },
                navigationIcon = {
                    IconButton(onNavigateUp) {
                        Icon(
                            painter = painterResource(CommonResources.drawable.common_resources_arrow_back),
                            contentDescription = stringResource(R.string.feature_onboarding_cd_navigate_up),
                        )
                    }
                },
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeContentPadding()
                    .background(
                        brush = Brush.verticalGradient(
                            0f to MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                            1f to MaterialTheme.colorScheme.background,
                        )
                    ),
            ) {
                PrimaryButton(
                    label = "Continue",
                    onClick = onContinueClick,
                    enabled = state.canProceedFurther,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.large),
                )
            }
        }
    ) { paddingValues ->
        when (state) {
            ConfigScreenState.Loading -> LoadingContent()
            is ConfigScreenState.Success -> ConfigScreenContent(
                viewModel = viewModel,
                state = state as ConfigScreenState.Success,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ConfigScreenContent(
    viewModel: ConfigScreenViewModel,
    state: ConfigScreenState.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        InfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.large),
        )

        CategorySelectionSection(
            categories = state.categories,
            onCategoryToggled = {
                viewModel.setCategoryIdFollowed(it.name, it.isFollowed)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.large),
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        Text(
            text = stringResource(R.string.feature_onboarding_config_screen_general_section_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(MaterialTheme.spacing.large),
        )

        EnableNotificationsTile(
            enabled = state.userData.notificationsEnabled,
            onChange = { enabled ->
                viewModel.setNotificationsEnabled(enabled)
            },
        )

        DarkThemeConfigTile(
            darkThemeConfig = state.userData.darkThemeConfig,
            onChange = { config ->
                if (config != state.userData.darkThemeConfig) {
                    viewModel.setDarkThemeConfig(config)
                }
            }
        )
    }
}

@Composable
fun EnableNotificationsTile(
    enabled: Boolean,
    onChange: (enabled: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                painter = painterResource(CommonResources.drawable.common_resources_notifications),
                contentDescription = stringResource(R.string.feature_onboarding_config_screen_enable_notifications_title)
            )
        },
        headlineContent = {
            Text(stringResource(R.string.feature_onboarding_config_screen_enable_notifications_title))
        },
        supportingContent = {
            Text(stringResource(R.string.feature_onboarding_config_screen_enable_notifications_subtitle))
        },
        trailingContent = {
            Switch(
                checked = enabled,
                onCheckedChange = { onChange(enabled.not()) }
            )
        }
    )
}

@Composable
fun DarkThemeConfigTile(
    darkThemeConfig: DarkThemeConfig,
    onChange: (config: DarkThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                painter = painterResource(CommonResources.drawable.common_resources_dark_mode),
                contentDescription = stringResource(R.string.feature_onboarding_config_screen_dark_mode_title)
            )
        },
        headlineContent = {
            Text(stringResource(R.string.feature_onboarding_config_screen_dark_mode_title))
        },
        supportingContent = {
            Text(stringResource(R.string.feature_onboarding_config_screen_dark_mode_subtitle))
        },
        trailingContent = {
            ThemeSelectionButton(
                darkThemeConfig = darkThemeConfig,
                onChange = onChange
            )
        }
    )
}

@Composable
fun ThemeSelectionButton(
    darkThemeConfig: DarkThemeConfig,
    onChange: (config: DarkThemeConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    TextButton(
        onClick = { expanded = true },
        modifier = modifier
    ) {
        Text(
            text = darkThemeConfig.getStringResource(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall)
        )

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DarkThemeConfig.entries.forEach { config ->
            val title = config.getStringResource()

            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        painter = config.getIconPainterResource(),
                        contentDescription = title
                    )
                },
                text = { Text(title) },
                onClick = {
                    expanded = false
                    onChange(config)
                }
            )
        }
    }
}

/**
 * Get string resource for [DarkThemeConfig]
 */
@Composable
private fun DarkThemeConfig.getStringResource() = when (this) {
    DarkThemeConfig.FOLLOW_SYSTEM -> stringResource(CommonResources.string.common_resources_theme_mode_system)
    DarkThemeConfig.LIGHT -> stringResource(CommonResources.string.common_resources_theme_mode_light)
    DarkThemeConfig.DARK -> stringResource(CommonResources.string.common_resources_theme_mode_dark)
}

/**
 * Get icon painter for [DarkThemeConfig]
 */
@Composable
private fun DarkThemeConfig.getIconPainterResource() = when (this) {
    DarkThemeConfig.FOLLOW_SYSTEM -> painterResource(CommonResources.drawable.common_resources_devices)
    DarkThemeConfig.LIGHT -> painterResource(CommonResources.drawable.common_resources_light_mode)
    DarkThemeConfig.DARK -> painterResource(CommonResources.drawable.common_resources_dark_mode)
}

@Composable
private fun InfoBanner(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small,
                Alignment.CenterHorizontally,
            ),
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface,
            ) {
                Icon(
                    painter = painterResource(CommonResources.drawable.common_resources_info),
                    contentDescription = null,
                )

                Text(
                    text = stringResource(R.string.feature_onboarding_config_screen_info_banner_msg),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySelectionSection(
    modifier: Modifier = Modifier,
    categories: List<FollowableCategory>,
    onCategoryToggled: (category: FollowableCategory) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.feature_onboarding_config_screen_categories_section_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))

            Text(
                text = stringResource(R.string.feature_onboarding_config_screen_categories_section_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.large))

            ContextualFlowRow(
                itemCount = categories.size,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) { idx ->
                val category = categories[idx]
                SelectableChip(
                    label = category.name,
                    selected = category.isFollowed,
                    onClick = {
                        onCategoryToggled(category.copy(isFollowed = !category.isFollowed))
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(CommonResources.drawable.common_resources_check),
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}
