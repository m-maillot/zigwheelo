//
//  ZWButton.swift
//  iosApp
//
//  Created by Martial Maillot on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ZWButtonStyle: ButtonStyle {
    var color: Color
    var style: ZWButton.Style
    
    func makeBody(configuration: ButtonStyle.Configuration) -> some View {
        switch style {
        case .fill: return AnyView(FillButton(color: color, configuration: configuration))
        case .outline: return AnyView(OutlineButton(color: color, configuration: configuration))
        case .ghost: return AnyView(GhostButton(color: color, configuration: configuration))
        }
    }
    
    struct FillButton: View {
        var color: Color
        let configuration: ButtonStyle.Configuration
        @Environment(\.isEnabled) private var isEnabled: Bool
        var body: some View {
            configuration.label
                .zwTypo(.s1)
                .foregroundColor(isEnabled ? .zwOnPrimary : .zwFontDisabled)
                .padding()
                .frame(minHeight: 56)
                .background(isEnabled ? color : Color.zwPrimary.opacity(0.2))
                .clipShape(Capsule())
                .opacity(configuration.isPressed ? 0.7 : 1)
        }
    }
    
    struct OutlineButton: View {
        var color: Color
        let configuration: ButtonStyle.Configuration
        @Environment(\.isEnabled) private var isEnabled: Bool
        var body: some View {
            configuration.label
                .zwTypo(.s1)
                .foregroundColor(isEnabled ? color : .zwFontDisabled)
                .padding()
                .frame(minHeight: 56)
                .background(isEnabled ? color.opacity(0.2) : Color.zwBasic.opacity(0.15))
                .clipShape(Capsule())
                .overlay(
                    RoundedRectangle(cornerRadius: 60)
                        .stroke(isEnabled ? color : Color.zwBasic.opacity(0.5), lineWidth: 1)
                )
                .opacity(configuration.isPressed ? 0.7 : 1)
        }
    }
    
    struct GhostButton: View {
        var color: Color
        let configuration: ButtonStyle.Configuration
        @Environment(\.isEnabled) private var isEnabled: Bool
        var body: some View {
            configuration.label
                .zwTypo(.s1)
                .foregroundColor(isEnabled ? color : .zwFontDisabled)
                .padding()
                .frame(minHeight: 56)
                .background(Color.white)
                .clipShape(Capsule())
                .opacity(configuration.isPressed ? 0.7 : 1)
        }
    }
}

// MARK: - Usage

extension Button {
    /// Changes the appearance of the button
    func style(_ style: ZWButton.Style, color: Color) -> some View {
        self.buttonStyle(ZWButtonStyle(color: color, style: style))
    }
}

struct ZWButton: View {
    
    enum Style {
        case fill, outline, ghost
    }
    
    var text: String?
    var image: Image?
    var style: Style = .fill
    var color: Color = .zwPrimary
    var action: () -> Void
    var textAndImage: Bool { text != nil && image != nil }
    
    var body: some View {
        Button(action: action, label: {
            HStack() {
                Spacer()
                HStack(spacing: textAndImage ? 12 : 0) {
                    Text(text ?? "")
                    image
                }
                Spacer()
            }
        }).style(style, color: color)
    }
}


// MARK: - Preview

public struct Input_Previews: PreviewProvider {
    static let cloudImg = Image(systemName: "cloud.sun")
    
    public static var previews: some View {
        VStack(spacing: 40) {
            
            HStack(spacing: 5) {
                ZWButton(text: "Fill", style: .fill, action: { print("click") })
                ZWButton(text: "Outline", style: .outline, action: { print("click") })
                ZWButton(text: "Ghost", style: .ghost, action: { print("click") })
            }
            
            HStack(spacing: 5) {
                ZWButton(text: "Danger", color: Color.red, action: { print("click") })
                ZWButton(text: "Warning", color: Color.orange, action: { print("click") })
                ZWButton(text: "Success", color: Color.green, action: { print("click") })
            }
            
            HStack(spacing: 5) {
                ZWButton(text: "Disabled", style: .fill, action: { print("click") })
                    .disabled(true)
                ZWButton(text: "Disabled", style: .outline, action: { print("click") })
                    .disabled(true)
                ZWButton(text: "Disabled", style: .ghost, action: { print("click") })
                    .disabled(true)
            }
            
            HStack(spacing: 5) {
                ZWButton(text: "Text", action: { print("click") })
                ZWButton(text: "Text", image: cloudImg, action: { print("click") })
                ZWButton(image: cloudImg, action: { print("click") })
            }
            
            Button(action: { print("click") }, label: { Text("Custom") })
                .style(.outline, color: Color.gray)
        }
    .padding(10)
    }
}
