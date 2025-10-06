package ipca.example.lastminutenews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.lastminutenews.models.Article
import ipca.example.lastminutenews.ui.theme.LastMinuteNewsTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

@Composable
fun ArticlesListView(modifier: Modifier = Modifier) {
    var articles by remember { mutableStateOf(arrayListOf<Article>()) }

    LazyColumn {
        itemsIndexed(
            items = articles
        ){ index, item ->
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                Column {
                    Text(item.title ?: "")
                    Text(item.description ?: "")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    val result = response.body!!.string()
                    val jsonResult = JSONObject(result)
                    if (jsonResult.getString("status") == "ok") {
                        val jsonArticles = jsonResult.getJSONArray("articles")

                        val articlesList = arrayListOf<Article>()
                        for( i in 0..<jsonArticles.length()){
                            val jsonArticle = jsonArticles.get(i) as JSONObject
                            val article = Article.fromJson(jsonArticle)
                            articlesList.add(article)
                        }
                        articles = articlesList
                    }

                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    LastMinuteNewsTheme {
        ArticlesListView()
    }
}