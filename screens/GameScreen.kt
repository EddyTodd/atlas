package com.ynmidk.atlas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasNavigation

@Composable
internal fun GameScreen(
    showAdBanner: Boolean = true,
    gameplayView: @Composable () -> Unit
) {
    val c = LocalAtlasComponents.current
    val navigation = LocalAtlasNavigation.current
    val context = LocalContext.current
    val appTitle = context.applicationInfo.loadLabel(context.packageManager).toString()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = appTitle,
                onLeadingIconClick = navigation.onNavigateBack,
                onTrailingIconClick = navigation.onOpenSettings
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = if (showAdBanner) 90.dp else 0.dp)
                        .fillMaxWidth()
                ) {
                    gameplayView()
                }

                if (showAdBanner) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(Color.Gray)
                    ) {
                        Text(
                            text = "AD BANNER",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
