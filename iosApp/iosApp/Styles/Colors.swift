import SwiftUI


extension Color {
    
    // MARK: Basic Colors
    
    static let zwBackground = Color("background")
    static let zwBasic = Color("basic")
    static let zwPrimary = Color("primary")
    static let zwOnPrimary = Color("onPrimary")
    
    // MARK: Font Colors
    
    /// Standard Font Color
    static let zwFontStd = Color("font_std")
    /// Hint Font Color
    static let zwFontHint = Color("font_hint")
    /// Disabled Font Color
    static let zwFontDisabled = Color("font_disabled")
    /// Button Font Color
    static let zwFontBtn = Color("font_button")
    
    // MARK: Semantic Colors
    
    static let zwDanger = Color("danger")
    static let zwInfo = Color("info")
    static let zwSuccess = Color("success")
    static let zwWarning = Color("warning")
    
    // MARK: State Colors
    
    /// Active State Color - Primary Style
    static let zwActivePrimary = Color("activePrimary")
    /// Active State Color - Basic Style
    static let zwActiveBasic = Color("activeBasic")
    
    // MARK: TextField Colors
    
    static let zwTextFieldBackground = Color("textFieldBackground")
}

struct Color_Previews: PreviewProvider {
    static var previews: some View {
        VStack(alignment: .center, spacing: 10) {
            HStack{
                Rectangle().size(CGSize(width: 50, height: 50))
                    .foregroundColor(.zwBasic)
                Rectangle().size(CGSize(width: 50, height: 50))
                    .foregroundColor(.zwPrimary)
                Rectangle().size(CGSize(width: 50, height: 50))
                    .foregroundColor(.zwSuccess)
                Rectangle().size(CGSize(width: 50, height: 50))
                .foregroundColor(.zwInfo)
                Rectangle().size(CGSize(width: 50, height: 50))
                .foregroundColor(.zwWarning)
                Rectangle().size(CGSize(width: 50, height: 50))
                .foregroundColor(.zwDanger)
            }
            Text("Hello, World!")
                .foregroundColor(.zwActivePrimary)
                .background(Color.zwActiveBasic)
                .environment(\.colorScheme, .dark)
        }
    .padding()
    }
}
