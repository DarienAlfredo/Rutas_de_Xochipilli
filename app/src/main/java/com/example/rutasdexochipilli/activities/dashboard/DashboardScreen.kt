package com.example.rutasdexochipilli.activities.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DashboardScreen() {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    var showText by remember { mutableStateOf(false) }
    var bottomBarOffset by remember { mutableIntStateOf(0) } //Estado para el offset
    val tag = "DashboardScreen"

    LaunchedEffect(sheetState.currentValue) { // Efecto con clave
        bottomBarOffset = if (sheetState.currentValue == SheetValue.PartiallyExpanded) {
            (sheetState.requireOffset() / 3).roundToInt()
        } else {
            0
        }
        Log.d(tag, "bottomBarOffset: $bottomBarOffset")
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
        .padding(16.dp)) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 200.dp,
            sheetContent = {
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Text("Contenido del Dashboard (Mapa aquí)")
                if (showText) {
                    Text("¡Has hecho clic en Visitar!")
                }
            }
        }
        // BottomAppBar superpuesta
        if (sheetState.currentValue != SheetValue.Hidden) {
            MyBottomBar(
                onVisitarClick = { showText = true },
                modifier = Modifier.offset { IntOffset(0, -bottomBarOffset) }
            )
        }
    }
}