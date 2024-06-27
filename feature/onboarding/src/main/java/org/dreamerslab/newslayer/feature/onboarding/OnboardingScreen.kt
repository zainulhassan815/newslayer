package org.dreamerslab.newslayer.feature.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onContinueClick: () -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false,
    ) { page ->
        if (page == 0) {
            WelcomeScreen(
                onNextClick = {
                    scope.launch { pagerState.animateScrollToPage(1) }
                }
            )
        }

        if (page == 1) {
            ConfigScreen(
                onNavigateUp = {
                    scope.launch { pagerState.animateScrollToPage(0) }
                },
                onContinueClick = onContinueClick,
                viewModel = viewModel()
            )
        }
    }
}
