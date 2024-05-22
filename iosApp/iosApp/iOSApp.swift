import SwiftUI
import ComposeApp

class AppDelegate : UIResponder, UIApplicationDelegate {
    lazy var applicationComponent: IosApplicationComponent = createApplicationComponent(
        appDelegate: self
    )
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
            let uiComponent = createIosUIViewControllerComponent(
                applicationComponent: delegate.applicationComponent
            )
			ContentView(component: uiComponent)
		}
	}
}

private func createApplicationComponent(
    appDelegate: AppDelegate
) -> IosApplicationComponent {
    return IosApplicationComponent.companion.create()
}

private func createIosUIViewControllerComponent(
    applicationComponent: IosApplicationComponent
) -> IosUIViewControllerComponent {
    return IosUIViewControllerComponent.companion.create(
        applicationComponent: applicationComponent
    )
}
