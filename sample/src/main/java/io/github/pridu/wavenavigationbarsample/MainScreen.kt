package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.pridu.wavenavigationbar.GetPaddingValues
import io.github.pridu.wavenavigationbar.WaveNavigationBar
import io.github.pridu.wavenavigationbar.WaveNavigationBarItem
import io.github.pridu.wavenavigationbar.WaveNavigationBarItemColors
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("home", "Home", Icons.Default.Home, "Home"),
    CALL("call", "Call", Icons.Default.Call, "Call"),
    FACE("face", "Face", Icons.Default.Face, "Face"),
    SHOP("shop", "Shop", Icons.Default.Shop, "Shop"),
    INFO("info", "Info", Icons.Default.Info, "Info"),
}

class BottomNavActions(private val navController: NavHostController) {
    fun navigateTo(destination: Destination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        BottomNavActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        when (navBackStackEntry?.destination?.route ?: Destination.HOME.route) {
            Destination.HOME.route -> 0
            Destination.CALL.route -> 1
            Destination.FACE.route -> 2
            Destination.SHOP.route -> 3
            Destination.INFO.route -> 4
            else -> 0
        }

    val navDestination = navigationActions::navigateTo

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Destination.entries.getOrNull(selectedDestination)?.let {
                        Text(text = it.label)
                    } ?: Text(text = Destination.HOME.label)
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier,
                selectedDestination = selectedDestination,
                onItemClicked = { destination ->
                    navDestination(destination)
                }
            )
        }
    ) { contentPadding ->
        AppNavHost(
            paddingValues = GetPaddingValues(contentPadding),
            navController = navController
        )
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedDestination: Int,
    onItemClicked: (Destination) -> Unit,
) {
    WaveNavigationBar(
        modifier = modifier.fillMaxWidth(),
        selectedItemIndex = selectedDestination,
        totalItems = Destination.entries.size,
        waveHeight = 24.dp
    ) {
        Destination.entries.forEachIndexed { index, destination ->
            WaveNavigationBarItem(
                selectedItem = selectedDestination == index,
                onClick = { onItemClicked(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(destination.label) },
                isColors = true,
                colors = WaveNavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.HOME.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen(paddingValues = paddingValues)
                    Destination.CALL -> CallScreen(paddingValues = paddingValues)
                    Destination.FACE -> FaceScreen(paddingValues = paddingValues)
                    Destination.SHOP -> ShopScreen(paddingValues = paddingValues)
                    Destination.INFO -> InfoScreen(paddingValues = paddingValues)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    WaveNavigationBarSampleTheme {
        MainScreen()
    }
}