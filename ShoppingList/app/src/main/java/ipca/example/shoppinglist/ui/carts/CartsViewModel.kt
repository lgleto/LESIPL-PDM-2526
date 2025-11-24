package ipca.example.shoppinglist.ui.carts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.shoppinglist.models.Cart
import ipca.example.shoppinglist.repositories.CartRepository
import ipca.example.shoppinglist.repositories.ResultWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


data class CartsState(
    var carts: List<Cart> = emptyList(),
    var error: String? = null,
    var isLoading: Boolean? = null
)

@HiltViewModel
class CartsViewModel @Inject constructor(
    val cartRepository: CartRepository
)
    : ViewModel() {

    var uiState = mutableStateOf(CartsState())
        private set

    val db = Firebase.firestore
    fun fetchCarts() {
        cartRepository.fetchCarts().onEach {result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        carts = result.data?: emptyList(),
                        error = null,
                        isLoading = false
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)


    }

    fun addCart(){

        val uid = Firebase.auth.currentUser?.uid!!
        val cart = Cart(name = "New Cart ${
            uiState.value.carts.size + 1
        }",owners = listOf(uid))

        cartRepository.addCart(cart).onEach { result->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        error = null,
                        isLoading = false
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)


    }


}
