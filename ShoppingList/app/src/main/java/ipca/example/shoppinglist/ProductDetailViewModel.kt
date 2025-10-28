package ipca.example.shoppinglist;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


data class ProductDetailState(
    var product: Product? = null,
    var name : String? = null,
    var qtd : Double? = null,
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class ProductDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductDetailState())
        private set

    fun setName(name : String) {
        uiState.value = uiState.value.copy(name = name)
    }

    fun setQtd(qtd : Double) {
        uiState.value = uiState.value.copy(qtd = qtd)
    }

    fun fetchProduct( docId : String) {
        uiState.value = uiState.value.copy(isLoading = true)
        val db = Firebase.firestore
        db.collection("products")
            .document(docId)
            .get()
            .addOnSuccessListener {
                uiState.value = uiState.value.copy(
                    product = it.toObject(Product::class.java),
                    name = it.data?.get("name").toString(),
                    qtd = it.data?.get("qtd").toString().toDouble(),
                    error = null,
                    isLoading = false
                )
            }
    }

    fun createProduct() {
        uiState.value = uiState.value.copy(isLoading = true)
        val db = Firebase.firestore
        db.collection("products")
            .add(Product(
                name = uiState.value.name,
                qtd =uiState.value.qtd
            )).addOnSuccessListener {

            }


    }

}
