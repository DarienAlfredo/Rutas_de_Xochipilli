package com.example.rutasdexochipilli.activities.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rutasdexochipilli.R

data class BottomMenuItem(
    val label: String,
    val icon: Painter
)

@Composable
fun MyBottomBar(onVisitarClick: () -> Unit, modifier: Modifier = Modifier) { // Nuevo parámetro modifier
    val bottomMenuItemslist = prepareBottomMenu()
    val viajarLabel = stringResource(R.string.viajar)
    var selectedItem by remember { mutableStateOf(viajarLabel) }

    BottomAppBar(
        modifier = modifier, // Aplica el modifier aquí
        containerColor = colorResource(R.color.light_background),
        tonalElevation = 3.dp
    ) {
        bottomMenuItemslist.forEach { bottomMenuItem ->
            val isSelected = selectedItem == bottomMenuItem.label
            val interactionSource = remember { MutableInteractionSource() }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource, // Sin efecto visual
                        indication = null,
                        onClick = {
                            selectedItem = bottomMenuItem.label
                            //  ¡Aquí ejecutamos la acción al hacer clic en "Visitar"!
                            if (bottomMenuItem.label == viajarLabel) {
                                onVisitarClick()
                            }
                        }
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomBarItemContent(
                        item = bottomMenuItem,
                        isSelected = isSelected
                    )
                    Text(
                        text = bottomMenuItem.label,
                        fontSize = 12.sp,
                        color = if (isSelected) {
                            colorResource(R.color.toast_brown)
                        } else {
                            colorResource(R.color.dark_brown)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBarItemContent(item: BottomMenuItem, isSelected: Boolean) {
    val animatedWidth = remember { Animatable(if(isSelected) 0.5f else 0f) }
    val density = LocalDensity.current
    val backgroundColor = colorResource(R.color.toast_brown).copy(alpha = 0.2f) // Color de fondo

    LaunchedEffect(isSelected) {
        if (isSelected || animatedWidth.value > 0) { // Animación en selección o si ya hay ancho
            animatedWidth.animateTo(
                targetValue = if (isSelected) 0.5f else 0f,
                animationSpec = tween(400)
            )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(36.dp)
                .drawBehind {
                    // Modificación aquí: solo dibujar si isSelected es true
                    if (isSelected && animatedWidth.value > 0) {
                        val halfWidthPx = size.width * animatedWidth.value
                        drawRoundRect(
                            color = backgroundColor,
                            size = Size(halfWidthPx * 2, size.height),
                            topLeft = Offset(size.width / 2 - halfWidthPx, 0f),
                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                            style = Fill
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (isSelected) {
                        when (item.label) {
                            stringResource(R.string.visitar) -> R.drawable.map_search_filled
                            stringResource(R.string.viajar) -> R.drawable.navigation_filled
                            stringResource(R.string.retos) -> R.drawable.bookmark_filled
                            else -> R.drawable.ic_launcher_foreground
                        }
                    } else {
                        when (item.label) {
                            stringResource(R.string.visitar) -> R.drawable.map_search
                            stringResource(R.string.viajar) -> R.drawable.navigation
                            stringResource(R.string.retos) -> R.drawable.bookmark
                            else -> R.drawable.ic_launcher_foreground
                        }
                    }
                ),
                contentDescription = item.label,
                tint = if (isSelected) {
                    colorResource(R.color.toast_brown)
                } else {
                    colorResource(R.color.dark_brown)
                },
                modifier = Modifier.size(24.dp) // Tamaño del icono
            )
        }
        // El texto ya está en el label del NavigationBarItem y no necesita resaltado adicional
    }
}

@Composable // ¡Ahora es Composable!
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = stringResource(R.string.visitar), icon = painterResource(R.drawable.map_search)),
        BottomMenuItem(label = stringResource(R.string.viajar), icon = painterResource(R.drawable.navigation)),
        BottomMenuItem(label = stringResource(R.string.retos), icon = painterResource(R.drawable.bookmark))
    )
}