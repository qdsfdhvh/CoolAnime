import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    private let component: IosUIViewControllerComponent

    init(component: IosUIViewControllerComponent) {
        self.component = component
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        return component.uiViewControllerFactory()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let component: IosUIViewControllerComponent

    init(component: IosUIViewControllerComponent) {
        self.component = component
    }

    var body: some View {
        ComposeView(component: self.component)
            .ignoresSafeArea(.all, edges: .all)
    }
}



