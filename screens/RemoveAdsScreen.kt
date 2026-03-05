package com.ynmidk.atlas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.atlasVerticalScroll
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun RemoveAdsScreen(
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val typography = LocalAtlasTypography.current
    val features = listOf(
        RemoveAdsFeature(
            icon = "🚫",
            title = "No Banner Ads",
            description = "Remove the banner ad at the bottom of the game screen for a cleaner view"
        ),
        RemoveAdsFeature(
            icon = "⏭️",
            title = "No Interstitial Ads",
            description = "Skip full-screen ads between games and jump right into your next round"
        ),
        RemoveAdsFeature(
            icon = "💡",
            title = "Free Hints",
            description = "No more watching ads to earn hints, get unlimited hints included"
        ),
        RemoveAdsFeature(
            icon = "❤️",
            title = "Support Development",
            description = "Help us keep building and improving Mines"
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Remove Ads",
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
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(colors.accent.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✨",
                            fontSize = 36.sp,
                            style = typography.textStyle(AtlasTextStyle.Body)
                        )
                    }
                    Text(
                        text = "Go Ad-Free",
                        style = typography.textStyle(AtlasTextStyle.Title),
                        color = colors.text
                    )
                    Text(
                        text = "Enjoy an uninterrupted minesweeper experience with a one-time purchase",
                        style = typography.textStyle(AtlasTextStyle.Caption),
                        color = colors.textCaption,
                        textAlign = TextAlign.Center
                    )
                }

                features.forEach { feature ->
                    c.Card(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = feature.icon,
                                style = typography.textStyle(AtlasTextStyle.Body),
                                fontSize = 20.sp
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = feature.title,
                                    style = typography.textStyle(AtlasTextStyle.CardTitle),
                                    color = colors.text
                                )
                                Text(
                                    text = feature.description,
                                    style = typography.textStyle(AtlasTextStyle.Caption),
                                    color = colors.textCaption
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.accent.copy(alpha = 0.08f))
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    colors.accent.copy(alpha = 0.45f),
                                    colors.accent.copy(alpha = 0.2f)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(colors.accent)
                                .padding(horizontal = 12.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "BEST VALUE",
                                style = typography.textStyle(AtlasTextStyle.Overline),
                                color = Color.White
                            )
                        }
                        Text(
                            text = "$2.99",
                            style = typography.textStyle(AtlasTextStyle.DisplayTitle),
                            color = colors.text
                        )
                        Text(
                            text = "One-time purchase - Forever",
                            style = typography.textStyle(AtlasTextStyle.Caption),
                            color = colors.textCaption,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                c.Button(
                    variant = ButtonVariant.Primary,
                    enabled = true,
                    onClick = {},
                    label = "Purchase - $2.99",
                    modifier = Modifier.fillMaxWidth()
                )

                c.Button(
                    variant = ButtonVariant.Text,
                    enabled = true,
                    onClick = {},
                    label = "Restore Purchase",
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Payment will be charged at confirmation of purchase. This is a one-time, non-recurring charge.",
                    style = typography.textStyle(AtlasTextStyle.Overline),
                    color = colors.textCaption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )
            }
        }
    }
}

private data class RemoveAdsFeature(
    val icon: String,
    val title: String,
    val description: String
)
