package com.ynmidk.atlas.core

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
fun Modifier.atlasVerticalScroll(state: ScrollState): Modifier =
    this
        .fadeTopEdge()
        .verticalScroll(state)

@Stable
fun Modifier.atlasScrollableContainer(): Modifier = this.fadeTopEdge()
