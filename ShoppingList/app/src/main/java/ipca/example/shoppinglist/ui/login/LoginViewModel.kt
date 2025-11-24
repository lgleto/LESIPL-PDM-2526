package ipca.example.shoppinglist.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.shoppinglist.repositories.CartRepository
import ipca.example.shoppinglist.repositories.LoginRepository
import ipca.example.shoppinglist.repositories.ResultWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


data class LoginState(
    var username: String? = null,
    var password: String?  = null,
    var error: String? = null,
    var isLoading: Boolean? = null
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepository
): ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun setUsername(username: String) {
        uiState.value = uiState.value.copy(username = username)
    }

    fun setPassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess : ()-> Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.username.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Username is required",
                isLoading = false
            )
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Password is required",
                isLoading = false
            )
        }
        // do login

        loginRepository.login(
            uiState.value.username!!,
            uiState.value.password!!
        ).onEach {result ->
            when(result){
                is ResultWrapper.Success -> {
                    onLoginSuccess()
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
