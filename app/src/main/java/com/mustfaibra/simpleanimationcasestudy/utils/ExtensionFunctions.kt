package com.mustfaibra.simpleanimationcasestudy.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


/** An extension function that is used to convert the px values as Int to a valid Dp */
@Composable
fun Int.getDp() : Dp {
    val px = this
    with(LocalDensity.current){
        return px.toDp()
    }
}

/** An extension function that is used to convert the px values as Int to a valid Dp */
@Composable
fun Float.getDp() : Dp {
    val px = this
    with(LocalDensity.current){
        return px.toDp()
    }
}