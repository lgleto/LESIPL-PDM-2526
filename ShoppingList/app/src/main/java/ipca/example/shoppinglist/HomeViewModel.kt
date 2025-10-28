package ipca.example.shoppinglist;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


data class HomeState(
    var products: List<Product> = emptyList(),
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class HomeViewModel : ViewModel() {

    var uiState = mutableStateOf(HomeState())
        private set

    val db = Firebase.firestore
    fun fetchProducts() {
        uiState.value = uiState.value.copy(isLoading = true)


        db.collection("products")
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
        db.collection("products")
            .document(docId)
            .update(mapOf("checked" to isChecked))
    }


}
