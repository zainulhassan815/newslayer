package org.dreamerslab.newslayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.dreamerslab.newslayer.ui.R

private val fontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

private val defaultTypography = Typography()

internal val NewsLayerTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = fontFamily
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = fontFamily
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = fontFamily
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = fontFamily,
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = fontFamily,
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = fontFamily
    )
)
