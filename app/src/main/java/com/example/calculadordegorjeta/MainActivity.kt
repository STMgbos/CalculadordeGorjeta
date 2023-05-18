package com.example.calculadordegorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    var gastoTotal by remember { mutableStateOf("") }
    var porcentagemGorjeta by remember { mutableStateOf("") }
    var arredondar by remember { mutableStateOf(false) }
    var gorjeta by remember { mutableStateOf(0.0) }

    val gerenciadorFocus = LocalFocusManager.current

    gorjeta = CalcularGorjeta(gastoTotal, porcentagemGorjeta, arredondar) // chama a função para calcular o arredondamento, recebe o valor e guarda na variavel

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.LightGray
    ) {
        Column( // estrutura o conteúdo em coluna
            verticalArrangement = Arrangement.Top, // coloca o conteudo no topo da tela
            horizontalAlignment = Alignment.CenterHorizontally, // coloca o conteudo da coluna alinhado no centro horizontal
            modifier = Modifier
                .padding(30.dp)
        ) {
            Text(
                text ="Calculador de Gorjeta",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.size(20.dp))

            CampoTextoeditavel(
                value = gastoTotal,
                label = "Valor Gasto",
                onValueChange = {gastoTotal = it},
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {gerenciadorFocus.moveFocus(FocusDirection.Next)}
                )
            )

            CampoTextoeditavel(
                value = porcentagemGorjeta,
                label = "% Gorjeta",
                onValueChange = {porcentagemGorjeta = it},
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onNext = {gerenciadorFocus.clearFocus()}
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Arredondar"
                )
                Switch(
                    checked = arredondar,
                    onCheckedChange = {arredondar=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),


                )
                
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text ="Valor da Gorjeta: ${NumberFormat.getCurrencyInstance().format(gorjeta)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoTextoeditavel(
    value:String,
    label:String,
    onValueChange:(String)->Unit,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        label = { Text(text = label)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    )
}

fun CalcularGorjeta(
    gastoTotal:String,
    porcentagemGorjeta:String,
    arredondar:Boolean
):Double{

    var gorjeta = (gastoTotal.toDoubleOrNull()?:0.0) * (porcentagemGorjeta.toDoubleOrNull()?:0.0)/100

    if(arredondar)
        gorjeta = kotlin.math.ceil(gorjeta)

    return gorjeta

}