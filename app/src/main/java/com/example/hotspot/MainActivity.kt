package com.example.hotspot

import android.content.Context
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.VueModele.navigation.NavBarBody
import com.example.hotspot.VueModele.navigation.NavBarHeader
import com.example.hotspot.VueModele.navigation.NavigationItem
import com.example.hotspot.VueModele.navigation.Screens
import com.example.hotspot.VueModele.navigation.SetUpNavGraph
import com.example.hotspot.ui.theme.HotSpotTheme
import kotlinx.coroutines.launch
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotSpotTheme {
                // A surface container using the 'background' color from the theme
                /*
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf (
                        NavigationItem(
                            title = "Home",
                            route = Screens.Home.route,
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Filled.Home,
                        ),
                        NavigationItem(
                            title = "Profil",
                            route = Screens.Profil.route,
                            selectedIcon = Icons.Filled.Person,
                            unselectedIcon = Icons.Filled.Person,
                        ),
                        NavigationItem(
                            title = "Favoris",
                            route = Screens.Favoris.route,
                            selectedIcon = Icons.Filled.Favorite,
                            unselectedIcon = Icons.Filled.Favorite,
                        ),

                        )

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currrentRoute = navBackStackEntry?.destination?.route
                    val topbarTitle =
                        if (currrentRoute != null){
                            items[items.indexOfFirst {
                                it.route == currrentRoute
                            }].title
                        }else{
                            items[0].title
                        }

                    ModalNavigationDrawer(

                        drawerContent = {
                            ModalDrawerSheet {
                                NavBarHeader()
                                Spacer(modifier = Modifier.height(8.dp))
                                NavBarBody(items = items, currentRoute = currrentRoute) {currentNavigationItem->
                                    navController.navigate(currentNavigationItem.route)
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            }
                        }, drawerState = drawerState) {
                        Scaffold(
                            topBar ={
                                TopAppBar(
                                    title = {
                                        Text(text = topbarTitle)
                                    }, navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(imageVector = Icons.Default.Menu, contentDescription = "MENU")
                                        }
                                    })
                            }
                        ) { innerPadding->
                            SetUpNavGraph(navController = navController, innerPadding = innerPadding)
                        }
                    }
                }*/

                val items = listOf (
                    NavigationItem(
                        title = "Home",
                        route = Screens.Home.route,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Filled.Home,
                    ),
                    NavigationItem(
                        title = "Profil",
                        route = Screens.Profil.route,
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Filled.Person,
                    ),
                    NavigationItem(
                        title = "Favoris",
                        route = Screens.Favoris.route,
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Filled.Favorite,
                    ),

                    )
                val selected = remember{
                    mutableStateOf(Icons.Default.Home)
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currrentRoute = navBackStackEntry?.destination?.route
                val topbarTitle =
                    if (currrentRoute != null){
                        items[items.indexOfFirst {
                            it.route == currrentRoute
                        }].title
                    }else{
                        items[0].title
                    }

                ModalNavigationDrawer(

                    drawerContent = {
                        ModalDrawerSheet {
                            NavBarHeader()
                            Spacer(modifier = Modifier.height(8.dp))
                            NavBarBody(items = items, currentRoute = currrentRoute) {currentNavigationItem->
                                navController.navigate(currentNavigationItem.route)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }
                    }, drawerState = drawerState) {
                    Scaffold(
                        bottomBar = {
                                    Surface (
                                        modifier = Modifier
                                            .shadow(elevation = 8.dp)
                                            .fillMaxWidth()
                                            .height(75.dp)
                                            .padding(all = 10.dp),
                                        shape = RoundedCornerShape(40), // Utilisation d'une forme personnalisée ovale

                                    ){
                                        BottomAppBar(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            tonalElevation = 8.dp,
                                            modifier = Modifier.background(color = Color.Transparent)
                                            ) {
                                            IconButton(onClick = {
                                                selected.value = Icons.Default.Home
                                                navController.navigate(Screens.Home.route)
                                            },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp),
                                                    tint = if (selected.value == Icons.Default.Home) Color.White else Color.Black
                                                )
                                            }
                                            IconButton(onClick = {
                                                selected.value = Icons.Default.Favorite
                                                navController.navigate(Screens.Favoris.route)
                                            },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(26.dp),
                                                    tint = if (selected.value == Icons.Default.Favorite) Color.White else Color.Black
                                                )
                                            }
                                            IconButton(onClick = {
                                                selected.value = Icons.Default.Person
                                                navController.navigate(Screens.Profil.route)
                                            },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(26.dp),
                                                    tint = if (selected.value == Icons.Default.Person) Color.White else Color.Black
                                                )
                                            }
                                        }
                                    }

                        },
                        topBar ={
                            TopAppBar(
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .shadow(elevation = 0.dp)
                                    .background(color = Color.Transparent),
                                title = {
                                    Text(text = topbarTitle)
                                }, navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Menu, contentDescription = "MENU")
                                    }
                                })
                        }

                    ) { innerPadding->
                        SetUpNavGraph(navController = navController, innerPadding = innerPadding, context = this)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HotSpotTheme {
       // MainScreen()
    }
}