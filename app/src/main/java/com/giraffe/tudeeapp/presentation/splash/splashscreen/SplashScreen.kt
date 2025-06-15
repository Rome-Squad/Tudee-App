package com.giraffe.tudeeapp.presentation.splash.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onOnboardingShown: () -> Unit,
    onOnboardingNotShown: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    val isOnboardingShown by viewModel.isOnboardingShown.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkOnboardingStatus()
    }

    LaunchedEffect(isOnboardingShown) {
        if (isOnboardingShown != null) {
            delay(3000)
            if (isOnboardingShown == true) {
                onOnboardingShown()
            } else {
                onOnboardingNotShown()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Theme.color.overlay,
                        Theme.color.surface
                    ),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            )
    ) {
        Image(
            painter = painterResource(id = Theme.resources.bacgroundImage),
            contentDescription = stringResource(R.string.splash_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
        )

        Image(
            painter = painterResource(id = Theme.resources.logoImageResId),
            contentDescription = stringResource(R.string.splash_logo),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
