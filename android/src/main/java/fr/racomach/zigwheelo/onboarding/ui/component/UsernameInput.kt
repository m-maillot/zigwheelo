package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import arrow.core.Either
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTypography
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameInput(
    modifier: Modifier = Modifier,
    onSubmit: (username: String) -> Unit
) {
    val username = remember { mutableStateOf<String?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    fun validate(username: String?): Either<String?, String> {
        if (username == null) {
            return Either.Left("Veuillez indiquer un pseudo")
        }
        if (username.length <= 3) {
            return Either.Left("Votre pseudo doit contenir au moins 3 charactères")
        }
        return Either.Right(username)
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Choississez d'abord un pseudo", style = ZigWheeloTypography.labelLarge)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value ?: "",
            singleLine = true,
            onValueChange = {
                error.value = null
                username.value = it
            },
            isError = error.value != null,
            supportingText = {
                error.value?.let { Text(it) }
            }
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                validate(username.value)
                    .tap(onSubmit)
                    .tapLeft { error.value = it }
            },
        ) {
            Text(text = "Continuer")
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun UsernameInputPreview() {
    ZigwheeloTheme3 {
        Box(modifier = Modifier.fillMaxSize()) {
            UsernameInput {}
        }
    }
}