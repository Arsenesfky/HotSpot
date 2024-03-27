package com.example.hotspot.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.VueModele.navigation.Screens

/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun HomeScreen(innerPadding: PaddingValues) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                Text(text = "")
                })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box (
                    modifier = Modifier
                        .width(300.dp)
                        .height(250.dp)
                ){
                    Text(
                        text = "Bienvenue \n trouvez des salles tout près de chez vous",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(300.dp))
                
                Button(
                    onClick = { /* Action à effectuer sur le clic du bouton */ },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(text = "Inscrivez - vous")
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Vous avez déja un compte ? Cliquez ici",
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center

                        )
                        )

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HotSpotTheme {
        //HomeScreen(innerPadding: PaddingValues)
    }
}
*/


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
