package com.example.rutasdexochipilli.activities.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyBottomSheet(modifier: Modifier = Modifier) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded, // Hoja inicialmente expandida
        skipHiddenState = true // Opcional: Evita que la hoja se pueda ocultar completamente
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp, // Altura visible inicial de la hoja
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp), // Altura máxima de la hoja
                contentAlignment = Alignment.Center
            ) {
                // Contenido de la hoja
            }
        }
    ) { contentPadding ->
        // Contenido del Scaffold
        Column(modifier = Modifier.padding(contentPadding)) {
            // Contenido debajo de la BottomSheet
        }
    }
}