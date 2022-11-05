package fr.racomach.zigwheelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import fr.racomach.api.createApi
import fr.racomach.api.usecase.SearchParkAction
import fr.racomach.api.usecase.SearchParks
import fr.racomach.zigwheelo.common.missingPermissions
import fr.racomach.zigwheelo.parks.ui.MainScreen
import fr.racomach.zigwheelo.ui.theme.ZigWheeloTheme

class MainActivity : ComponentActivity() {

    private lateinit var searchParks: SearchParks;

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

        searchParks = SearchParks(createApi("http://192.168.1.35:9580", true))
        val action = SearchParkAction.Search(45.742989978188945, 4.851021720981201, 500)
        searchParks.dispatch(action)

        setContent {
            ZigWheeloTheme {
                val state = searchParks.observeState().collectAsState().value
                MainScreen(state = state)
            }
        }
    }
}