package ipca.example.shoppinglist.ui.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.models.Product


data class ProductState(
    var products: List<Product> = emptyList(),
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class ProductViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductState())
        private set

    var cartId : String? = null

    val db = Firebase.firestore
    fun fetchProducts(cartId : String) {
        this.cartId = cartId
        uiState.value = uiState.value.copy(isLoading = true)


        db
            .collection("carts")
            .document(cartId)
            .collection("products")
            .addSnapshotListener { result, error ->
                if (error != null) {
                    uiState.value = uiState.value.copy(
                        error = error.message,
                        isLoading = false
                    )
                    return@addSnapshotListener
                }

                var products = mutableListOf<Product>()
                for (document in result?.documents?:emptyList()) {
                    var product = document.toObject(Product::class.java)
                    product?.docId = document.id
                    //product.name = document.data["name"].toString()
                    //product.qtd = document.data["qtd"].toString().toDouble()
                    product?.let {
                        products.add(product)
                    }

                }
                uiState.value = uiState.value.copy(
                    products = products,
                    error = null,
                    isLoading = false
                )
            }

    }

    fun checkProduct(docId: String, isChecked: Boolean) {

        db
            .collection("carts")
            .document(cartId!!)
            .collection("products")
            .document(docId)
            .update(mapOf("checked" to isChecked))
    }


}
