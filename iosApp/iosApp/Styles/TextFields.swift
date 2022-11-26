import SwiftUI


struct UnderlinedTextFieldStyle: TextFieldStyle {
    var focus: Bool
    var hasError: Bool
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(.horizontal)
            .padding(.vertical)
            .background(
                ZStack {
                    Color.zwTextFieldBackground
                    VStack {
                        Spacer()
                        (hasError ? Color.zwDanger : focus ? Color.zwPrimary : Color(UIColor.systemGray4))
                            .frame(height: 2)
                    }
                }
            )
    }
}

struct ZWTextField: View {
    
    var label: String
    var text: Binding<String>
    var error: String? = nil
    
    @FocusState private var focusState: Bool
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField(label, text: text)
                .focused($focusState)
                .onChange(of: focusState, perform: { _ in })
                .textFieldStyle(UnderlinedTextFieldStyle(focus: focusState, hasError: error != nil))
            if let error = error {
                Text(error).zwTypo(.c1).foregroundColor(.zwDanger).padding(.horizontal)
            }
        }
    }
}

// MARK: Preview

struct ZWTextField_Previews: PreviewProvider {
    
    static var previews: some View {
        VStack(spacing: 20) {
            ZWTextField(label: "Thats a default Textfield", text: .constant(""))
            ZWTextField(label: "Thats a default Textfield", text: .constant(""), error: "This is an error")
        }
        .padding()
    }
}
