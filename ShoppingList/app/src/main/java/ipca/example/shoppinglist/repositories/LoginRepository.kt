package ipca.example.shoppinglist.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.shoppinglist.models.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun login(username : String, password : String) : Flow<ResultWrapper<Unit>> = flow{
        try {
            emit(ResultWrapper.Loading())
            val result = auth.signInWithEmailAndPassword(
                username,
                password)
                .await()

            result.user?.email?.let {
                db.collection("users")
                    .document(result.user!!.uid)
                    .set(mapOf("email" to it))
                    .await()
            }

            emit(ResultWrapper.Success(Unit))

        }catch (e:Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)


}