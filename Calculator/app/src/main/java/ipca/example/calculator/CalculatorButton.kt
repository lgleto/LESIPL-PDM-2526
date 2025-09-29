package ipca.example.calculator

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ipca.example.calculator.ui.theme.CalculatorTheme
import ipca.example.calculator.ui.theme.Orange
import ipca.example.calculator.ui.theme.Purple40

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    label : String,
    isOperation : Boolean = false,
    onClick: (String) -> Unit
){
    Button(onClick = { onClick(label) },
        modifier = modifier
            .size(90.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isOperation ) Orange else Purple40
        )
    ) {
        Text(
            label,
            fontSize = TextUnit(value = 42f, type = TextUnitType.Sp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview(){
    CalculatorTheme {
        CalculatorButton(
            label = "1",
            onClick = {}
        )
    }
}