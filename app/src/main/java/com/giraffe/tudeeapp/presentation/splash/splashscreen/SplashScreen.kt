package com.giraffe.tudeeapp.presentation.splash.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.splash.SplashViewModel
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Theme.color.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Theme.color.overlay
                )
        ) {
            Image(
                painter = painterResource(id = Theme.drawables.bacgroundImage),
                contentDescription = stringResource(R.string.splash_background),
                modifier = Modifier
                    .fillMaxWidth(), contentScale = ContentScale.FillWidth
            )

            Image(
                painter = painterResource(id = Theme.drawables.logoImageResId),
                contentDescription = stringResource(R.string.splash_logo),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
