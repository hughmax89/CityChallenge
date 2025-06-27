package com.aguerodev.citychallenge.view.core

    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavBackStackEntry
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.toRoute
    import com.aguerodev.citychallenge.view.detailCity.DetailScreen
    import com.aguerodev.citychallenge.view.detailCity.DetailViewModel
    import com.aguerodev.citychallenge.view.home.HomeScreen
    import com.aguerodev.citychallenge.view.home.HomeViewModel
    import com.aguerodev.citychallenge.view.splash.SplashScreen

    @Composable
    fun NavigationWrapper(
        modifier: Modifier = Modifier
    ) {
        val navController: NavHostController = rememberNavController()
        NavHost(navController = navController, startDestination = Splash) {
            composable<Splash> {
                SplashScreen(navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo<Home> {
                            inclusive = true
                        }
                    }
                })
            }
            composable<Home> {
                HomeScreen(
                    navigateToDetail = { id -> navController.navigate(Detail(id = id)) }
                )
            }
            composable<Detail> { navStackEntry ->
                var detail = navStackEntry.toRoute<Detail>()
                DetailScreen(
                    _id = detail.id,
                    navigateToHome = { navController.popBackStack() }
                )
            }
        }
    }