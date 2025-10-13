package ipca.example.lastminutenews.ui.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.lastminutenews.models.Article
import ipca.example.lastminutenews.ui.theme.LastMinuteNewsTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder

@Composable
fun ArticlesListView(
    modifier: Modifier = Modifier,
    navController: NavController,
    source : String
    ) {

    val viewModel : ArticlesListViewModel = viewModel()
    val uiState by viewModel.uiState

    ArticlesListViewContent(
        modifier = modifier,
        uiState = uiState,
        navController = navController
    )

    LaunchedEffect(Unit) {
        viewModel.loadArticles(source)
    }
}

@Composable
fun ArticlesListViewContent(
    modifier: Modifier = Modifier,
    uiState: ArticlesListState,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(uiState.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = uiState.articles
                ) { index, item ->
                    ArticleViewCell( article = item){
                            navController
                                .navigate("details/${item.title}/${item.url?.encodeURL()}")
                        }
                }
            }
        }
    }
}

fun String.encodeURL() : String {
    return URLEncoder.encode(this, "UTF-8")
}

@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    LastMinuteNewsTheme {
        ArticlesListViewContent(
            navController = rememberNavController(),
            uiState = ArticlesListState(
                isLoading = true,
                error = "No internet connection!",
                articles = listOf(
                    Article(
                        title = "Title 1",
                        description = "Description 1"
                    ),
                    Article(
                        title = "Title 2",
                        description = "Description 2"
                    )
                )
            )
        )
    }
}