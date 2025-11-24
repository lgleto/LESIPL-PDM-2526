package ipca.example.shoppinglist

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun firestore() : FirebaseFirestore {
        val db = FirebaseFirestore.getInstance()
        return db
    }

    @Provides
    @Singleton
    fun auth() : FirebaseAuth = Firebase.auth

}