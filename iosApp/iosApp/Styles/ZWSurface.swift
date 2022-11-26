import Foundation

struct ZWSurface: ZStackStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .background(ZWColors.primary)
            .foregroundColor(ZWColors.background)
            .clipShape(Capsule())
    }
}

struct ZWSurface_Previews: PreviewProvider {
    static var previews: some View {
        Button("Hello world !") {
            
        }.buttonStyle(ZWButton())
    }
}
