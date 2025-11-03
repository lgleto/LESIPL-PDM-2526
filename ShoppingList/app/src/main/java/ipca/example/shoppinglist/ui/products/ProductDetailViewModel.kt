package ipca.example.shoppinglist.ui.products

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.models.Product


data class ProductDetailState(
    var name : String? = null,
    var qtd : Double? = null,
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class ProductDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductDetailState())
        private set

    var docId : String? = null

    fun setName(name : String) {
        uiState.value = uiState.value.copy(name = name)
    }

    fun setQtd(qtd : Double) {
        uiState.value = uiState.value.copy(qtd = qtd)
    }

    fun fetchProduct( cartId : String,docId : String) {
        this.docId = docId
        uiState.value = uiState.value.copy(isLoading = true)
        val db = Firebase.firestore
        db
            .collection("carts")
            .document(cartId)
            .collection("products")
            .document(docId)
            .get()
            .addOnSuccessListener {
                uiState.value = uiState.value.copy(
                    name = it.data?.get("name").toString(),
                    qtd = (it.data?.get("qtd")?:"0.0").toString().toDouble(),
                    error = null,
                    isLoading = false
                )
            }
    }

    fun createProduct(cartId : String) {
        uiState.value = uiState.value.copy(isLoading = true)
        val db = Firebase.firestore
        if (docId == null) {

            db
                .collection("carts")
                .document(cartId)
                .collection("products")
                .add(
                    Product(
                        name = uiState.value.name,
                        qtd = uiState.value.qtd
                    )
                ).addOnSuccessListener {

                }
        }else{
            db
                .collection("carts")
                .document(cartId)
                .collection("products")
                .document(docId!!)
                .update(
                    mapOf(
                        "name" to uiState.value.name,
                        "qtd" to uiState.value.qtd)
                )

        }

    }

}
