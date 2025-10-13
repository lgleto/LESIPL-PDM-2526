package ipca.example.lastminutenews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.lastminutenews.ui.theme.LastMinuteNewsTheme

@Composable
fun MyBottomBar(
    navController: NavController
){
    var index by remember { mutableStateOf(0) }
    BottomAppBar {
        NavigationBarItem(
            selected = index == 0,
            onClick = {
                index = 0
                navController.navigate("techcrunch")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "TechCrunch"
                )
            },
            label = {
                Text("TechCrunch")
            }
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = {
                index = 1
                navController.navigate("bloomberg")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "BloomBerg"
                )
            },
            label = {
                Text("BloomBerg")
            }
        )
        NavigationBarItem(
            selected = index == 2,
            onClick = {
                index = 2
                navController.navigate("espn")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "ESPN"
                )
            },
            label = {
                Text("ESPN")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    LastMinuteNewsTheme {
        MyBottomBar(
            rememberNavController()
        )
    }
}