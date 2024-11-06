@file:Suppress("unused", "FunctionName")

package com.kgalligan.mapview

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.compose.swift.bridge.ComposeNativeViewFactory
import co.touchlab.compose.swift.bridge.LocalNativeViewFactory
import platform.UIKit.UIViewController

fun MainViewController(
    generatedViewFactory: ComposeNativeViewFactory
): UIViewController = ComposeUIViewController {
    CompositionLocalProvider(
        LocalNativeViewFactory provides generatedViewFactory,
    ) {
        App()
    }
}