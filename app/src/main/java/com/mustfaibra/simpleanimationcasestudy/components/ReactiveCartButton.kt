package com.mustfaibra.simpleanimationcasestudy.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mustfaibra.simpleanimationcasestudy.R

@Composable
fun ReactiveCartIcon(
    modifier: Modifier = Modifier,
    isOnCart: Boolean,
    onCartChange: () -> Unit,
) {
    val transition =
        updateTransition(targetState = isOnCart, label = "cart")

    val tint by transition.animateColor(label = "tint") {
        when (it) {
            true -> MaterialTheme.colors.primary
            false -> MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
        }
    }
    val rotation by transition.animateFloat(label = "rotation") { if (it) 360f else -360f }
    Icon(
        painter = painterResource(id = R.drawable.ic_shopping_bag),
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .clip(CircleShape)
            .clickable { onCartChange() }
            .padding(4.dp)
            .size(20.dp),
        tint = tint
    )
}