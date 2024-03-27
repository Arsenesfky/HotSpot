package com.example.hotspot.VueModele.navigation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hotspot.R

data class NavigationItem(
    val title : String,
    val route : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val badgeCount : Int? = null
)

@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),contentDescription = "logo",
            modifier = Modifier.size(100.dp).padding(top=10.dp)
        )
        Text(
            text = "HOTSPOT",
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarBody(
    items : List<NavigationItem>,
    currentRoute : String ?,
    onClick : (NavigationItem)-> Unit
){
    items.forEachIndexed {
            index, navigationItem ->
        NavigationDrawerItem(
            label = {
                Text(text = navigationItem.title) },
            selected = currentRoute == navigationItem.route ,
            onClick = { onClick( navigationItem) },
            icon = {
                Icon(
                    imageVector = if (currentRoute == navigationItem.route)
                    {navigationItem.selectedIcon }
                    else
                    {navigationItem.unselectedIcon},
                    contentDescription = navigationItem.title)
            })
    }
}
