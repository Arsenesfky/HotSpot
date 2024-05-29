package com.example.hotspot.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivities
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.R
import com.example.hotspot.Vue.HomeActivity
import com.example.hotspot.Vue.Profil.ProfilScreenContent
import com.example.hotspot.VueModele.navigation.Screens
import com.example.hotspot.VueModele.navigation.startDetailScreenActivity
import com.example.hotspot.afterLogin
import com.example.hotspot.ui.theme.HotSpotTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Mot de passe") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                val uid = currentUser.uid
                                // Enregistrer l'UID dans les préférences partagées
                                saveUserUid(uid, context)
                                // Appeler la fonction de succès de connexion
                                onLoginSuccess(uid)
                            }
                        } else {
                            // Erreur lors de la connexion
                            val errorMessage = when (task.exception) {
                                is FirebaseAuthInvalidUserException -> "Utilisateur non trouvé"
                                is FirebaseAuthInvalidCredentialsException -> "Identifiants invalides"
                                else -> "Erreur de connexion"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Se connecter")
        }
    }
}

private fun saveUserUid(uid: String, context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString("user_uid", uid)
    editor.apply()
}
/*
@Composable
fun LoginPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Mot de passe") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val auth = FirebaseAuth.getInstance()
                            val currentUser = auth.currentUser

                            if (currentUser != null) {
                                val uid = currentUser.uid

                                // Enregistrer l'UID dans les préférences partagées
                                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                                val editor = sharedPreferences.edit()
                                editor.putString("user_uid", uid)
                                editor.apply()
                            }
                            // Connexion réussie, l'utilisateur est connecté
                            Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show()
                            // Redirection vers la page AfterLogin
                            val navigate = Intent(context, HomeActivity::class.java)
                            context.startActivities(arrayOf(navigate))


                        } else {
                            // Erreur lors de la connexion
                            val errorMessage = when (task.exception) {
                                is FirebaseAuthInvalidUserException -> "Utilisateur non trouvé"
                                is FirebaseAuthInvalidCredentialsException -> "Identifiants invalides"
                                else -> "Erreur de connexion"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Se connecter")
        }
    }
}*/

fun signInWithEmailAndPassword(email: String, password: String, context: Context, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Connexion réussie, l'utilisateur est connecté
                Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show()
                 // Redirection vers la page AfterLogin


            } else {
                // Erreur lors de la connexion
                val errorMessage = when (task.exception) {
                    is FirebaseAuthInvalidUserException -> "Utilisateur non trouvé"
                    is FirebaseAuthInvalidCredentialsException -> "Identifiants invalides"
                    else -> "Erreur de connexion"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
}
@Composable
fun redirectToAfterLogin() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Profil.route
    ) {
        composable(Screens.AfterLogin.route) {
            // Vous pouvez insérer le contenu de DetailScreenActivity ici si nécessaire
            // ou vous pouvez simplement lancer l'activité directement depuis cette composable.
            afterLogin()
        }
    }
}
/*
@Composable
fun signInWithEmailAndPassword(email: String, password: String, context: Context) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Connexion réussie, l'utilisateur est connecté
                Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show()
                //afterLogin()
            } else {
                // Erreur lors de la connexion
                val errorMessage = when (task.exception) {
                    is FirebaseAuthInvalidUserException -> "Utilisateur non trouvé"
                    is FirebaseAuthInvalidCredentialsException -> "Identifiants invalides"
                    else -> "Erreur de connexion"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
}

 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    HotSpotTheme {
        //LoginPage()
    }
}