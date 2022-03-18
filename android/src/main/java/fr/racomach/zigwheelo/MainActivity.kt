package fr.racomach.zigwheelo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import fr.racomach.zigwheelo.common.missingPermissions
import fr.racomach.zigwheelo.parks.ui.MainScreen
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                // Do nothing
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val missingPermissions = missingPermissions()
        if (missingPermissions.isNotEmpty()) {
            requestMultiplePermissions.launch(missingPermissions.toTypedArray())
        }

        setContent {
            ZigWheeloTheme {
                val state = viewModel.uiState.collectAsState().value
                MainScreen(state = state)
            }
        }

        viewModel.load()
    }
}