package ipca.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import ipca.example.shoppinglist.ui.carts.CartsView

import ipca.example.shoppinglist.ui.login.LoginView
import ipca.example.shoppinglist.ui.product.ProductsView
import ipca.example.shoppinglist.ui.products.ProductDetailView
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("login") {
                            LoginView(navController)
                        }
                        composable("home") {
                            CartsView(navController)
                        }
                        composable("products/{cartId}") {
                            val cartId = it.arguments?.getString("cartId")
                            ProductsView(
                                navController = navController,
                                cartId = cartId!!
                            )
                        }
                        composable("productDetail/{cartId}") {
                            val cartId = it.arguments?.getString("cartId")
                            ProductDetailView(
                                navController = navController,
                                cartId = cartId!!
                            )
                        }
                        composable("productDetail/{cartId}/{productID}") {
                            val cartId = it.arguments?.getString("cartId")
                            val docId = it.arguments?.getString("productID")
                            ProductDetailView(
                                navController = navController,
                                docId = docId,
                                cartId = cartId!!
                            )
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                val currentUser = Firebase.auth.currentUser
                if (currentUser != null) {
                    navController.navigate("home")
                }
            }
        }
    }
}
