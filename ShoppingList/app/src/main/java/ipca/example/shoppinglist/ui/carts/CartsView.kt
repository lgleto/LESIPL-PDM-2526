package ipca.example.shoppinglist.ui.carts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun CartsView(
    navController: NavController,
    modifier: Modifier = Modifier
)  {

    val viewModel : CartsViewModel = viewModel()
    val uiState by viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.fetchCarts()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading == true) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(
                    uiState.error!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = uiState.carts
                    ) { index, item ->
                        Row (
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            CartViewCell(
                                cart = item,
                                onClick = {
                                    navController.navigate("products/${item.docId}")
                                },
                            )
                        }
                    }
                }
            }

        }
        Button(
            onClick = {
                viewModel.addCart()
            },
            modifier = Modifier.padding(12.dp)
        ) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    ShoppingListTheme {
        CartsView(navController = rememberNavController())
    }
}