package com.example.hotspot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.R
import com.example.hotspot.Vue.Profil.ProfilScreenOnPage
import com.example.hotspot.VueModele.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreenContent(persons: List<Person>, onItemClick: (Person) -> Unit) {
    Surface(modifier = Modifier.padding(top = 50.dp)) {
        LazyColumn(
            contentPadding = PaddingValues(12.dp)
        ) {
            items(items = persons) { person ->
                CustomItem(person = person) {
                    onItemClick(person)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
/*
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    val personRepository = PersonRepository()
    val getAllData = personRepository.getAllData()

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreenContent(getAllData) { person ->
                navController.navigate("itemDetails/${person.id}")
            }
        }
        composable("itemDetails/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            itemId?.let { id ->
                val person = getAllData.firstOrNull { it.id == id }
                person?.let {
                    ItemDetailsScreen(item = it)
                }
            }
        }
    }
}
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues){
    val categories = listOf(
        "Fete",
        "Reunion",
        "Hotel",
        "Espace vert"
    )
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .padding(top = 30.dp)
    ){it
        Column(
            modifier = Modifier
                .padding(all = 10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier
                    .height(height = 350.dp),
                    //.horizontalScroll(rememberScrollState())
                    //.padding(10.dp),
                content = {
                    ImageBoxExample()
                    // Ajoutez d'autres images ici
                }
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(text = "Categorie")
            LazyRow() {
                items(categories.chunked(2)) { rowCategories ->
                    Row(Modifier.fillMaxWidth()) {
                        rowCategories.forEach { category ->
                            CategoryItem(category)
                        }
                    }
                }
            }
            ProfilScreenOnPage()

        }

    }
}



@Composable
fun ItemDetailsScreen(item: Person) {
    Surface(modifier = Modifier.padding(top = 70.dp)){
        Text(text = "Page de : ${item.firstName} ${item.lastName} id : ${item.id}")

    }
    // Affiche les détails de l'élément spécifié
}

@Composable
fun CustomItem(person: Person, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "${person.age}",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = person.firstName,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = person.lastName,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)

class PersonRepository {
    fun getAllData(): List<Person> {
        return listOf(
            Person(
                id = 0,
                firstName = "Sefu",
                lastName = "Arsene",
                age = 20
            ),
            Person(
                id = 1,
                firstName = "Martial",
                lastName = "Katung",
                age = 21
            ),
            Person(
                id = 2,
                firstName = "Tegra",
                lastName = "Kamanda",
                age = 22
            ),
            Person(
                id = 3,
                firstName = "Mike",
                lastName = "Mwenda",
                age = 23
            ),
            Person(
                id = 4,
                firstName = "Nsenga",
                lastName = "Roethgen",
                age = 24
            )
        )
    }
}
@Composable
fun ImageBoxExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun CategoryItem(category: String) {
    Box(
        modifier = Modifier
            //.weight(1f)
            .padding(8.dp)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.test),
                contentDescription = null,
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = category,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}