package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasNavigation
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun HomeScreen(
    continueDescription: String?,
    onContinue: (() -> Unit)?,
    newGameOptionsContent: (@Composable () -> Unit)?,
    onStartGame: () -> Unit
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val navigation = LocalAtlasNavigation.current
    val context = LocalContext.current
    val logoSize = LocalConfiguration.current.screenWidthDp.dp / 4
    val appName = context.applicationInfo.loadLabel(context.packageManager).toString()
    var showNewGameOptions by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                onTrailingIconClick = navigation.onOpenSettings,
                title = ""
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    c.AppLogoBadge(badgeSize = logoSize)

                    Spacer(modifier = Modifier.height(8.dp))

                    c.Text(appName, AtlasTextStyle.DisplayTitle)

                    Spacer(modifier = Modifier.weight(2.5f))

                    if (continueDescription != null && onContinue != null) {
                        c.Card(
                            modifier = Modifier.fillMaxWidth(),
                            style = CardStyle.Tappable,
                            onClick = onContinue
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    c.Text("Continue", AtlasTextStyle.CardTitle)
                                    c.Text(continueDescription, AtlasTextStyle.Caption)
                                }
                                Icon(
                                    imageVector = atlasIcon(IconRole.ChevronRight),
                                    contentDescription = null,
                                    tint = colors.textMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    c.Button(
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.Primary,
                        enabled = true,
                        onClick = {
                            if (newGameOptionsContent == null) {
                                onStartGame()
                            } else {
                                showNewGameOptions = true
                            }
                        },
                        label = "New Game"
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }

    if (showNewGameOptions && newGameOptionsContent != null) {
        c.BottomDrawer(onDismiss = { showNewGameOptions = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                newGameOptionsContent()
                Spacer(modifier = Modifier.weight(1f))
                c.Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Primary,
                    enabled = true,
                    onClick = {
                        showNewGameOptions = false
                        onStartGame()
                    },
                    label = "New Game"
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
