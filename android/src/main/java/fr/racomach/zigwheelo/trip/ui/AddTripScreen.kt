package fr.racomach.zigwheelo.trip.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ajout d'un nouveau trajet"
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nom du trajet (facultatif)") },
                value = "",
                onValueChange = {},
                singleLine = true,
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Départ")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Heure du départ") },
                value = "",
                onValueChange = {},
                singleLine = true,
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Adresse du départ") },
                value = "",
                onValueChange = {},
                singleLine = true,
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Arrivée")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Adresse de l'arrivée") },
                value = "",
                onValueChange = {},
                singleLine = true,
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Durée")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Durée du trajet") },
                value = "",
                onValueChange = {},
                singleLine = true,
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
fun AddTripScreenPreview() {
    ZigwheeloTheme3 {
        AddTripScreen(modifier = Modifier.fillMaxSize())
    }
}