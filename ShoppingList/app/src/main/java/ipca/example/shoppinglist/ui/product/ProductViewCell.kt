package ipca.example.shoppinglist.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.shoppinglist.models.Product
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ProductViewCell(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
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
                product.name ?: "",
                modifier = Modifier.weight(1f),
                fontSize = 24.sp
            )
            Text(
                product.qtd.toString(),
                fontSize = 24.sp
            )
            Checkbox(
                checked = product.checked ?: false,
                onCheckedChange = {
                    onCheckedChange(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductViewCellPreview() {
    ShoppingListTheme {
        ProductViewCell(
            product = Product(
                name = "Product 1",
                qtd = 1.0,
                checked = false
            ),
            onClick = {},
            onCheckedChange = {}
        )
    }
}