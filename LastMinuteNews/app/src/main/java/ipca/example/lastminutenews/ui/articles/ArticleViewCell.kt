package ipca.example.lastminutenews.ui.articles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.lastminutenews.models.Article
import ipca.example.lastminutenews.ui.theme.LastMinuteNewsTheme

@Composable
fun ArticleViewCell (
    modifier: Modifier = Modifier,
    article: Article,
    onItemClick: () -> Unit = {}
){
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable{
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(article.title ?: "",
                fontSize = TextUnit(20f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
            )
            Text(article.description ?: "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleViewCellPreview(){
    LastMinuteNewsTheme {
        ArticleViewCell(article = Article(
            title = "Title",
            description = "Description",
        ))
    }
}
