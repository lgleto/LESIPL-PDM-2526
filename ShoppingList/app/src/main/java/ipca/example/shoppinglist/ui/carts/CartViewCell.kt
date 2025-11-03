package ipca.example.shoppinglist.ui.carts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.shoppinglist.models.Cart
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun CartViewCell(
    modifier: Modifier = Modifier,
    cart: Cart,
    onClick: () -> Unit
) {
    Card ( modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable{
            onClick()
        }){
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                cart.name ?: "",
                modifier = Modifier.weight(1f),
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductViewCellPreview() {
    ShoppingListTheme {
        CartViewCell(
            cart = Cart(
                name = "Cart 1",
            ),
            onClick = {}
        )
    }
}