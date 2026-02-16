package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.atlasVerticalScroll
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents

@Composable
internal fun TermsOfServiceScreen(
    onBack: (() -> Unit)?
) {
    LegalTextScreen(
        title = "Terms of Service",
        body = listOf(
            "By using this app, you agree to use it only for lawful purposes and in a way that does not harm the app, its services, or other users.",
            "The app is provided as-is without warranties of any kind. Availability and features may change at any time.",
            "You are responsible for maintaining the security of your device and your local app data."
        ),
        onBack = onBack
    )
}

@Composable
internal fun PrivacyPolicyScreen(
    onBack: (() -> Unit)?
) {
    LegalTextScreen(
        title = "Privacy Policy",
        body = listOf(
            "This app may store settings and gameplay data locally on your device to provide core functionality.",
            "No personal data is sold. If analytics or crash reporting are enabled by the host app, those services are governed by their own policies.",
            "You can clear local app data from your device settings at any time."
        ),
        onBack = onBack
    )
}

@Composable
internal fun LicensesScreen(
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    val licenses = listOf(
        "Kotlin",
        "Jetpack Compose",
        "AndroidX"
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Licenses",
                onLeadingIconClick = onBack
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .atlasVerticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                c.Text(
                    "Open source software used by this app:",
                    AtlasTextStyle.Muted
                )

                c.Card {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        licenses.forEach { item ->
                            c.Text(item, AtlasTextStyle.Body)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LegalTextScreen(
    title: String,
    body: List<String>,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = title,
                onLeadingIconClick = onBack
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .atlasVerticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                c.Card {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        body.forEach { paragraph ->
                            c.Text(paragraph, AtlasTextStyle.Body)
                        }
                    }
                }
            }
        }
    }
}
