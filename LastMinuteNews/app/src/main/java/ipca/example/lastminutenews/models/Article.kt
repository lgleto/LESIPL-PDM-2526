package ipca.example.lastminutenews.models

import org.json.JSONObject

data class Article (
    var title : String? = null,
    var author : String? = null,
    var description : String? = null,
    var url : String? = null,
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