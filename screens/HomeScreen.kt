package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun HomeScreen(
    displayTitle: String?,
    continueTitle: String?,
    continueSubtitle: String?,
    onContinue: (() -> Unit)?,
    onOpenSettings: (() -> Unit)?,
    onStartGame: () -> Unit
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val context = LocalContext.current
    val appName = context.applicationInfo.loadLabel(context.packageManager).toString()
    val effectiveDisplayTitle = displayTitle?.takeIf { it.isNotBlank() } ?: appName

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                onTrailingIconClick = onOpenSettings,
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

                    c.AppLogoBadge(badgeSize = 168.dp)
                    c.Text(effectiveDisplayTitle, AtlasTextStyle.DisplayTitle)

                    Spacer(modifier = Modifier.weight(3f))

                    if (continueTitle != null && continueSubtitle != null && onContinue != null) {
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
                                    c.Text(continueTitle, AtlasTextStyle.CardTitle)
                                    c.Text(continueSubtitle, AtlasTextStyle.Caption)
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
                        onClick = onStartGame,
                        label = "New Game"
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
