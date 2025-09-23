package ipca.example.helloworld

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.example.helloworld.ui.theme.HelloWorldTheme

@Composable
fun GreetView(modifier: Modifier = Modifier){

    Column(modifier = modifier)
    {
        TextField(value = "",
            onValueChange = {})
        Text("Hello world!")
        Text("Olá mundo!")
        Button(onClick = {}) {
            Text("Greet")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetViewPreview(){
    HelloWorldTheme {
        GreetView()
    }
}