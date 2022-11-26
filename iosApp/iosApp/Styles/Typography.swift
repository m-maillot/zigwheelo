import SwiftUI

public struct ZWTypography: ViewModifier {
    
    enum Style {
        
        /// Titles
        case h1, h2, h3, h4, h5, h6
        
        /// Subtitles
        case s1, s2
        
        /// Paragraphs
        case p1, p2
        
        /// Captions
        case c1, c2
    }
    
    var style: Style
    
    public func body(content: Content) -> some View {
        switch style {
        case .h1: return content
            .font(.system(size: 36, weight: .bold))
        case .h2: return content
            .font(.system(size: 32, weight: .bold))
        case .h3: return content
            .font(.system(size: 30, weight: .bold))
        case .h4: return content
            .font(.system(size: 26, weight: .bold))
        case .h5: return content
            .font(.system(size: 22, weight: .bold))
        case .h6: return content
            .font(.system(size: 18, weight: .bold))
            
        case .s1: return content
            .font(.system(size: 15, weight: .semibold))
        case .s2: return content
            .font(.system(size: 13, weight: .semibold))
            
        case .p1: return content
            .font(.system(size: 15, weight: .regular))
        case .p2: return content
            .font(.system(size: 13, weight: .regular))
            
        case .c1: return content
            .font(.system(size: 12, weight: .regular))
        case .c2: return content
            .font(.system(size: 12, weight: .bold))
        }
    }
}

extension View {
    func zwTypo(_ style: ZWTypography.Style) -> some View {
        self
            .modifier(ZWTypography(style: style))
    }
    
    func zwTypo(_ style: ZWTypography.Style, color: Color) -> some View {
        self
            .modifier(ZWTypography(style: style))
            .foregroundColor(color)
    }
}


struct Typography_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 10) {
            Group {
                Text("Typography h1").zwTypo(.h1, color: .zwPrimary)
                Text("Typography h1").zwTypo(.h1, color: .zwSuccess)
                Text("Typography h1").zwTypo(.h1, color: .zwDanger)
            
                Text("Typography h1").zwTypo(.h1)
                Text("Typography h2").zwTypo(.h2)
                Text("Typography h3").zwTypo(.h3)
                Text("Typography h4").zwTypo(.h4)
                Text("Typography h5").zwTypo(.h5)
                Text("Typography h6").zwTypo(.h6)
            }
            Group {
                Text("Typography h1").zwTypo(.s1)
                Text("Typography h2").zwTypo(.s2)
                
                Text("Typography p1").zwTypo(.p1)
                Text("Typography p2").zwTypo(.p2)
                
                Text("Typography c1").zwTypo(.c1)
                Text("Typography c2").zwTypo(.c2)
            }
        }
    }
}
