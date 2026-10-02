package com.example.tungclock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.material3.ExperimentalMaterial3Api


private val Fundo = Color(0xFF0B0E17)
private val CardFundo = Color(0xFF171D2B)
private val Roxo = Color(0xFF9B7CFF)
private val Texto = Color.White
private val TextoSecundario = Color(0xFF9CA3B5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FusosScreen(
    paddingValues: PaddingValues,
    fusos: SnapshotStateList<FusoHorario>,
    onVoltar: () -> Unit,
    onAbrirDetalhes: (Int) -> Unit
) {

    var cidade by remember {
        mutableStateOf("")
    }

    var pais by remember {
        mutableStateOf("")
    }

    var zona by remember {
        mutableStateOf("")
    }

    var offsetTexto by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = Fundo,
        topBar = {

            TopAppBar(
                title = {
                    Text("Fusos Horários")
                },
                navigationIcon = {

                    IconButton(
                        onClick = onVoltar
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Fundo)
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "ADICIONAR FUSO",
                color = Roxo,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            OutlinedTextField(
                value = cidade,
                onValueChange = {
                    cidade = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Cidade")
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = pais,
                onValueChange = {
                    pais = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("País")
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = zona,
                onValueChange = {
                    zona = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Zona")
                },
                placeholder = {
                    Text("Ex: America/Sao_Paulo")
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = offsetTexto,
                onValueChange = {
                    offsetTexto = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("UTC")
                },
                placeholder = {
                    Text("Ex: -3")
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Button(
                onClick = {

                    val offset = offsetTexto.toIntOrNull()

                    if (
                        cidade.isNotBlank() &&
                        pais.isNotBlank() &&
                        zona.isNotBlank() &&
                        offset != null
                    ) {

                        val novoId =
                            if (fusos.isEmpty()) {
                                1
                            } else {
                                fusos.maxOf {
                                    it.id
                                } + 1
                            }

                        fusos.add(
                            FusoHorario(
                                id = novoId,
                                cidade = cidade,
                                pais = pais,
                                zona = zona,
                                offset = offset
                            )
                        )

                        cidade = ""
                        pais = ""
                        zona = ""
                        offsetTexto = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Text(" ADICIONAR FUSO")
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "SEUS FUSOS",
                color = Texto,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = fusos,
                    key = {
                        it.id
                    }
                ) { fuso ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAbrirDetalhes(fuso.id)
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CardFundo)
                                .padding(16.dp)
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = fuso.cidade,
                                    color = Texto,
                                    fontSize = 18.sp
                                )

                                Text(
                                    text = fuso.pais,
                                    color = TextoSecundario,
                                    fontSize = 12.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = "UTC ${
                                        if (fuso.offset >= 0) "+"
                                        else ""
                                    }${fuso.offset}",
                                    color = Roxo,
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(
                                onClick = {
                                    fusos.remove(fuso)
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remover fuso",
                                    tint = Color(0xFFFF6B6B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
