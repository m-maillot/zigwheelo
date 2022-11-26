import SwiftUI
import Lottie

enum ZWAnimation : String, CaseIterable {
    case happy = "happy"
    case wink = "wink"
    case sad = "sad"
    case trip = "trip"
    case notification = "notification"
}

struct LottieAnimation: View {
    @Environment(\.colorScheme) var colorScheme
    
    var animation: ZWAnimation
    var loopMode: LottieLoopMode = .playOnce

    var body: some View {
        LottieView(
            name: "\(animation.rawValue)_\(colorScheme == .dark ? "dark" : "light")",
            loopMode: loopMode
        )
    }
}

struct LottieAnimation_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ForEach(ZWAnimation.allCases, id: \.self) { anim in
                LottieAnimation(animation: anim)
            }
        }.padding()
    }
}
