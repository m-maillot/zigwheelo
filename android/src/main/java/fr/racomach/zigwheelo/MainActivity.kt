package fr.racomach.zigwheelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import fr.racomach.zigwheelo.parks.ui.MainScreen
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZigWheeloTheme {
                val state = viewModel.uiState.collectAsState().value
                MainScreen(state = state)
            }
        }

        viewModel.load()
    }
}