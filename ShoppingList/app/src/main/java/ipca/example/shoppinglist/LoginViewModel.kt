package ipca.example.shoppinglist;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


data class LoginState(
    var username: String? = null,
    var password: String?  = null,
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class LoginViewModel : ViewModel() {

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

        var auth: FirebaseAuth
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(
            uiState.value.username!!,
            uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    uiState.value = uiState.value.copy(
                        error = null,
                        isLoading = false
                    )
                    onLoginSuccess()
                } else {
                    // If sign in fails, display a message to the user.

                    uiState.value = uiState.value.copy(
                        error = task.exception?.message,
                        isLoading = false
                    )
                }
            }

    }

}
