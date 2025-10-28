package ipca.example.shoppinglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun LoginView(
    navController: NavController,
    modifier: Modifier = Modifier
){

    val viewModel : LoginViewModel = viewModel()
    val uiState = viewModel.uiState.value

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(R.drawable.outline_shopping_cart_24),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        )
        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.username?:"",
            onValueChange = {
                viewModel.setUsername(it)
            },
            label = { Text("Username") }
        )
        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.password?:"",
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.setPassword(it)
            },
            label = { Text("Password") }
        )
        if(uiState.error != null) {
            Text(
                uiState.error?:"",
                modifier = Modifier.padding(8.dp),
            )
        }
        Button(onClick = {
            viewModel.login(onLoginSuccess = {
                navController.navigate("home")
            })
        }) {
            Text("Login",
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    ShoppingListTheme {
        LoginView(navController = rememberNavController())
    }
}