package ipca.example.shoppinglist.ui.carts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.shoppinglist.models.Cart


data class CartsState(
    var carts: List<Cart> = emptyList(),
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class CartsViewModel : ViewModel() {

    var uiState = mutableStateOf(CartsState())
        private set

    val db = Firebase.firestore
    fun fetchCarts() {
        uiState.value = uiState.value.copy(isLoading = true)
        db.collection("carts")
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { result, error ->
                if (error != null) {
                    uiState.value = uiState.value.copy(
                        error = error.message,
                        isLoading = false
                    )
                    return@addSnapshotListener
                }

                var carts = mutableListOf<Cart>()
                for (document in result?.documents?:emptyList()) {
                    var cart = document.toObject(Cart::class.java)
                    cart?.docId = document.id
                    cart?.let {
                        carts.add(cart)
                    }

                }
                uiState.value = uiState.value.copy(
                    carts = carts,
                    error = null,
                    isLoading = false
                )
            }

    }

    fun addCart(){

        val uid = Firebase.auth.currentUser?.uid!!

        uiState.value = uiState.value.copy(isLoading = true)
        db.collection("carts")
            .add(Cart(name = "New Cart ${
                uiState.value.carts.size + 1
            }",
                owners = listOf(uid)))
    }


}
