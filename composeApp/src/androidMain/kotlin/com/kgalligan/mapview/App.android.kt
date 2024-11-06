package com.kgalligan.mapview

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
actual fun ShowNativeText(modifier: Modifier, someText: SomeText) {
    Text(modifier = modifier, textAlign = TextAlign.Center, text = someText.s)
}