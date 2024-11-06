import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewController(generatedViewFactory: SwiftUINativeViewFactory())
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}

class SwiftUINativeViewFactory : NativeViewFactory {
    func createShowNativeText(observable: ComposeApp.ShowNativeTextObservable) -> AnyView {
        return AnyView(SimpleTextView(observable: observable))
    }
}

struct SimpleTextView : View {
    @ObservedObject var observable: ComposeApp.ShowNativeTextObservable
    
    var body: some View {
        Text(observable.someText.s)
        .padding()
    }
}


