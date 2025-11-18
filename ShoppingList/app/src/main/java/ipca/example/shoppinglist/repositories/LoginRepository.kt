package ipca.example.shoppinglist.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.shoppinglist.models.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    fun fetchCarts() : Flow<ResultWrapper<List<Cart>>> = flow{
        try {
            emit(ResultWrapper.Loading())
            db.collection("carts")
                .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
                .snapshotFlow()
                .collect { result ->
                    var carts = mutableListOf<Cart>()
                    for (document in result?.documents ?: emptyList()) {
                        var cart = document.toObject(Cart::class.java)
                        cart?.docId = document.id
                        cart?.let {
                            carts.add(cart)
                        }

                    }
                    emit(ResultWrapper.Success(carts.toList()))
                }

        }catch (e:Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun addCart(cart:Cart) : Flow<ResultWrapper<Unit>> = flow{
        try {
            emit(ResultWrapper.Loading())
            db.collection("carts")
                .add(cart)
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e:Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)
}