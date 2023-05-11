package com.example.calculadordegorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadordegorjeta.ui.theme.CalculadorDeGorjetaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadorDeGorjetaTheme {
                AppCalculadorDeGorjeta()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppCalculadorDeGorjeta(){
    var gastoTotal by remember { mutableStateOf("0") }
    var gorjeta by remember { mutableStateOf(0.0) }

    gorjeta = CalcularGorjeta(gastoTotal,15.0)

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.LightGray
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            Text(
                text ="Calculador de Gorjeta",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.size(20.dp))

            TextField(
                value = gastoTotal,
                onValueChange = { gastoTotal = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Gasto total")}
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text ="Valor da Gorjeta: ${NumberFormat.getCurrencyInstance().format(gorjeta)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
fun CalcularGorjeta(
    gastoTotal:String,
    percentagemGorjeta:Double
):Double{

    return (gastoTotal.toDouble()*percentagemGorjeta)/100

}