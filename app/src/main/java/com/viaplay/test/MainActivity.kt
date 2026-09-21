package com.viaplay.test

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.viaplay.test.common.ui.common.Routes
import com.viaplay.test.common.ui.common.Routes.Companion.LINK
import com.viaplay.test.common.ui.theme.AppTheme
import com.viaplay.test.common.utils.asLink
import com.viaplay.test.domain.model.Link
import com.viaplay.test.feature.details.DetailsScreen
import com.viaplay.test.feature.dashboard.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavGraph(navController)
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.LINKS.title) {
        composable(Routes.LINKS.title) {
            DashboardScreen(hiltViewModel()) { route ->
                navController.navigate(route)
            }
        }
        composable(
            Routes.Details.title, arguments = listOf(
                navArgument(LINK) {
                    type = LinkNavType()
                }
            )
        ) { from ->
            DetailsScreen(
                hiltViewModel(),
                from.arguments?.asLink(LINK, Link::class.java),
                navController::navigateUp
            )
        }
    }
}