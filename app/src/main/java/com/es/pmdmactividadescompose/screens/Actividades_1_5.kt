package com.es.pmdmactividadescompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview(showBackground = true)
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
@Preview(showBackground = true)
@Composable
fun Actividad2() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
            Spacer(Modifier.padding(bottom = 30.dp))
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Preview(showBackground = true)
@Composable
fun Actividad3() {
    var progress by rememberSaveable { mutableFloatStateOf(0f) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(progress = progress)

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { if (progress < 1f) progress += 0.1f }) {
                Text(text = "Incrementar")
            }
            Spacer(modifier = Modifier.padding(start = 15.dp))
            Button(onClick = { if (progress > 0f) progress -= 0.1f }) {
                Text(text = "Reducir")
            }
        }
    }
}

/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    val onValueChange: (String) -> Unit = { value ->
        val newValue = value
            .replace(",", ".")

        if (newValue.count { it == '.' } <= 1) {
            if (newValue.count { it == '.' } == 1 && newValue.substringAfter(".").count() <= 1) {
                myVal = newValue
            } else if (newValue.count { it == '.' } == 0) {
                myVal = newValue
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = myVal,
            onValueChange = onValueChange ,
            label = { Text(text = "Importe") }
        )
    }
}

/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Actividad5TextField (
        myVal = myVal,
        onValueChange = { value ->
            val newValue = value
                .replace(",", ".")

            if (newValue.count { it == '.' } <= 1 && newValue.all { it.isDigit() || it == '.' }) {
                if (newValue.count { it == '.' } == 1 && newValue.substringAfter(".").count() <= 1) {
                    myVal = newValue
                } else if (newValue.count { it == '.' } == 0) {
                    myVal = newValue
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5TextField(
    myVal: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = myVal,
            onValueChange = onValueChange,
            label = { Text(text = "Importe") },
            modifier = Modifier
                .padding(15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Red
            )
        )
    }
}
