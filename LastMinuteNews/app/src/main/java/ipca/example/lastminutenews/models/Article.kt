package ipca.example.lastminutenews.models

import androidx.annotation.NonNull
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import org.json.JSONObject

@Entity
data class Article (
    var title : String? = null,
    var author : String? = null,
    var description : String? = null,
    @PrimaryKey
    var url : String = "",
    var urlToImage : String? = null,
    var publishedAt : String? = null
){
    companion object {
        fun fromJson( jsonObject: JSONObject) : Article {
            return Article(
                title = jsonObject.getString("title"),
                author = jsonObject.getString("author"),
                description = jsonObject.getString("description"),
                url = jsonObject.getString("url"),
                urlToImage = jsonObject.getString("urlToImage"),
                publishedAt = jsonObject.getString("publishedAt")
            )
        }
    }
}

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE url=:url")
    fun loadByUrl(url: String): List<Article>

    @Insert
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}