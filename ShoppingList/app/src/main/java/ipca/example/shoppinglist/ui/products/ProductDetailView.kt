package ipca.example.shoppinglist.ui.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ProductDetailView(
    navController: NavController,
    modifier: Modifier = Modifier,
    docId: String? = null,
    cartId : String
){

    val viewModel : ProductDetailViewModel = viewModel()
    val uiState = viewModel.uiState.value

    LaunchedEffect(docId) {
        if (docId != null) {
            viewModel.fetchProduct(cartId, docId)
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.name?:"",
            onValueChange = {
                viewModel.setName(it)
            },
            label = { Text("product name") }
        )
        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.qtd?.toString()?:"",
             onValueChange = {
                viewModel.setQtd(it.toDouble())
            },
            label = { Text("Qtd") }
        )

        Button(onClick = {
            viewModel.createProduct(cartId)
            navController.popBackStack()
        }) {
            Text("Add",
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductDetailViewPreview() {
    ShoppingListTheme {
        ProductDetailView(navController = rememberNavController(), cartId = "")
    }
}