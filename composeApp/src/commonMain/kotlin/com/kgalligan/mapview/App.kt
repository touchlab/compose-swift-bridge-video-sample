package com.kgalligan.mapview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.compose.swift.bridge.ExpectSwiftView
import co.touchlab.compose.swift.bridge.ViewType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import csbmapview.composeapp.generated.resources.Res
import csbmapview.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            ShowNativeText(Modifier.fillMaxWidth()
                .height(30.dp), SomeText("I'm a native view!"))
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

data class SomeText(val s:String)

@ExpectSwiftView(
    type = ViewType.SwiftUI
)
@Composable
expect fun ShowNativeText(modifier: Modifier = Modifier, someText: SomeText)