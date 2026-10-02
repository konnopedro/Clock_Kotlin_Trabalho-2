package com.example.tungclock

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.os.Build
import androidx.annotation.RequiresApi




object Rotas {

    const val RELOGIO = "relogio"
    const val CRONOMETRO = "cronometro"
    const val TIMER = "timer"

    const val ALARMES = "alarmes"
    const val DETALHE_ALARME = "detalhe_alarme/{id}"

    const val FUSOS = "fusos"
    const val DETALHE_FUSO = "detalhe_fuso/{id}"
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {


    // ==============================
    // CONTROLADOR DE NAVEGAÇÃO
    // ==============================

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val rotaAtual = navBackStackEntry?.destination?.route


    // ==============================
    // LISTA DE ALARMES
    // ==============================

    val alarmes = remember {

        mutableStateListOf(

            Alarme(
                id = 1,
                horario = "07:00",
                nome = "Acordar",
                dias = "Segunda a sexta",
                ativo = true
            ),

            Alarme(
                id = 2,
                horario = "12:00",
                nome = "Almoço",
                dias = "Todos os dias",
                ativo = false
            )
        )
    }


    // ==============================
    // LISTA DE FUSOS HORÁRIOS
    // ==============================

    val fusos = remember {

        mutableStateListOf(

            FusoHorario(
                id = 1,
                cidade = "São Paulo",
                pais = "Brasil",
                zona = "America/Sao_Paulo",
                offset = -3
            ),

            FusoHorario(
                id = 2,
                cidade = "New York",
                pais = "Estados Unidos",
                zona = "America/New_York",
                offset = -4
            ),

            FusoHorario(
                id = 3,
                cidade = "Londres",
                pais = "Reino Unido",
                zona = "Europe/London",
                offset = 1
            )
        )
    }


    // ==============================
    // ESTRUTURA PRINCIPAL
    // ==============================

    Scaffold(

        bottomBar = {

            NavigationBar {

                // ==============================
                // RELÓGIO
                // ==============================

                NavigationBarItem(

                    selected = rotaAtual == Rotas.RELOGIO,

                    onClick = {

                        navController.navigate(
                            Rotas.RELOGIO
                        ) {

                            popUpTo(
                                Rotas.RELOGIO
                            )

                            launchSingleTop = true
                        }
                    },

                    icon = {
                        Text("◉")
                    },


                            label = {
                        Text("Relógio")
                    }
                )


                // ==============================
                // CRONÔMETRO
                // ==============================

                NavigationBarItem(

                    selected = rotaAtual == Rotas.CRONOMETRO,

                    onClick = {

                        navController.navigate(
                            Rotas.CRONOMETRO
                        ) {

                            launchSingleTop = true
                        }
                    },

                    icon = {
                        Text("⏱")
                    },



                            label = {
                        Text("Crono")
                    }
                )


                // ==============================
                // TIMER
                // ==============================

                NavigationBarItem(

                    selected = rotaAtual == Rotas.TIMER,

                    onClick = {

                        navController.navigate(
                            Rotas.TIMER
                        ) {

                            launchSingleTop = true
                        }
                    },

                    icon = {
                        Text("⏰")
                    },



                            label = {
                        Text("Timer")
                    }
                )
            }
        }

    ) { paddingValues ->


        // ==============================
        // NAVHOST
        // ==============================

        NavHost(

            navController = navController,

            startDestination = Rotas.RELOGIO

        ) {


            // ==========================================
            // TELA DO RELÓGIO
            // ==========================================

            composable(
                route = Rotas.RELOGIO
            ) {

                RelogioScreen(

                    paddingValues = paddingValues,

                    onAbrirAlarmes = {

                        navController.navigate(
                            Rotas.ALARMES
                        )
                    },

                    onAbrirFusos = {

                        navController.navigate(
                            Rotas.FUSOS
                        )
                    }
                )
            }


            // ==========================================
            // CRONÔMETRO
            // ==========================================

            composable(
                route = Rotas.CRONOMETRO
            ) {

                CronometroScreen(
                    paddingValues = paddingValues
                )
            }


            // ==========================================
            // TIMER
            // ==========================================

            composable(
                route = Rotas.TIMER
            ) {

                TimerScreen(
                    paddingValues = paddingValues
                )
            }


            // ==========================================
            // LISTA DE ALARMES
            // ==========================================

            composable(
                route = Rotas.ALARMES
            ) {

                AlarmesScreen(
                    paddingValues = paddingValues,
                    alarmes = alarmes,
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onAbrirDetalhes = { id: Int ->
                        navController.navigate(
                            "detalhe_alarme/$id"
                        )
                    }
                )

            }


            // ==========================================
            // DETALHE DO ALARME
            // ==========================================

            composable(

                route = Rotas.DETALHE_ALARME,

                arguments = listOf(

                    navArgument("id") {

                        type = NavType.IntType
                    }
                )

            ) { backStackEntry ->

                val id =
                    backStackEntry.arguments?.getInt("id")
                        ?: -1


                DetalheAlarmeScreen(

                    id = id,

                    alarmes = alarmes,

                    onVoltar = {

                        navController.popBackStack()
                    }
                )
            }


            // ==========================================
            // LISTA DE FUSOS HORÁRIOS
            // ==========================================

            composable(
                route = Rotas.FUSOS
            ) {

                FusosScreen(
                    paddingValues = paddingValues,
                    fusos = fusos,
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onAbrirDetalhes = { id: Int ->
                        navController.navigate(
                            "detalhe_fuso/$id"
                        )
                    }
                )

            }


            // ==========================================
            // DETALHE DO FUSO HORÁRIO
            // ==========================================

            composable(

                route = Rotas.DETALHE_FUSO,

                arguments = listOf(

                    navArgument("id") {

                        type = NavType.IntType
                    }
                )

            ) { backStackEntry ->

                val id =
                    backStackEntry.arguments?.getInt("id")
                        ?: -1


                DetalheFusoScreen(

                    id = id,

                    fusos = fusos,

                    onVoltar = {

                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
