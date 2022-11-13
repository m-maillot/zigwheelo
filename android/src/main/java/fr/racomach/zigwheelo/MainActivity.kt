package fr.racomach.zigwheelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.racomach.zigwheelo.onboarding.ui.OnboardingScreen
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZigwheeloTheme3 {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") { OnboardingScreen(viewModel = hiltViewModel()) }
                }
            }
        }
    }
}