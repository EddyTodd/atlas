package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.core.fadeTopEdge
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun SettingsScreen(
    title: String,
    sections: List<SettingsSection>,
    onBack: (() -> Unit)?,
    onSelectItem: (SettingsItem) -> Unit
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
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
                    .fadeTopEdge()
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                sections.forEach { section ->
                    section.items.forEach { item ->
                        c.Card(
                            style = CardStyle.Tappable,
                            onClick = { onSelectItem(item) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                c.Text(item.icon, AtlasTextStyle.Body)
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    c.Text(item.title, AtlasTextStyle.CardTitle)
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
                }
            }
        }
    }
}
