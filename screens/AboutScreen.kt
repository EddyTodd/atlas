package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.core.fadeTopEdge
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalColors
import java.util.Calendar

@Composable
internal fun DefaultAboutScreen(
    onBack: (() -> Unit)?,
    onOpenTermsOfService: (() -> Unit)?,
    onOpenPrivacyPolicy: (() -> Unit)?,
    onOpenLicenses: (() -> Unit)?
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val c = LocalAtlasComponents.current
    val logoSize = (configuration.screenWidthDp.dp / 4)

    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val appName = context.applicationInfo.loadLabel(context.packageManager).toString()
    val developer = "Ynmidk LLC"
    val version = "${packageInfo.versionName}.${packageInfo.longVersionCode}"
    val platform = "Android"
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val footer = "© $year $developer. All rights reserved."

    val aboutItems = listOf(
        "App Name" to appName,
        "Developer" to developer,
        "Version" to version,
        "Platform" to platform
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "About",
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
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    c.AppLogoBadge(badgeSize = logoSize)
                    Spacer(modifier = Modifier.height(8.dp))
                    c.Text(appName, AtlasTextStyle.Title)
                }

                Spacer(modifier = Modifier.height(8.dp))

                c.Card(contentPadding = PaddingValues(0.dp)) {
                    Column {
                        aboutItems.forEachIndexed { index, (label, value) ->
                            AboutRow(label, value)
                            if (index < aboutItems.lastIndex) {
                                c.Divider()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                LinkCard(
                    title = "Terms of Service",
                    onClick = onOpenTermsOfService
                )
                LinkCard(
                    title = "Privacy Policy",
                    onClick = onOpenPrivacyPolicy
                )
                LinkCard(
                    title = "Licenses",
                    onClick = onOpenLicenses
                )

                c.Text(footer, AtlasTextStyle.Caption)
            }
        }
    }
}

@Composable
private fun AboutRow(label: String, value: String) {
    val c = LocalAtlasComponents.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        c.Text(label, AtlasTextStyle.Muted)
        c.Text(value, AtlasTextStyle.CardTitle)
    }
}

@Composable
private fun LinkCard(
    title: String,
    onClick: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    c.Card(
        style = CardStyle.Tappable,
        enabled = onClick != null,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            c.Text(title, AtlasTextStyle.CardTitle)
            Icon(
                imageVector = atlasIcon(IconRole.ChevronRight),
                contentDescription = null,
                tint = colors.textMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
