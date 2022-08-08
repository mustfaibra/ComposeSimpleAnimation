package com.mustfaibra.simpleanimationcasestudy.components

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mustfaibra.simpleanimationcasestudy.ui.theme.DarkBlue
import com.mustfaibra.simpleanimationcasestudy.utils.getDp

@Composable
fun ProductItemLayout(
    modifier: Modifier = Modifier,
    image: Int,
    price: Double,
    title: String,
    onCart: Boolean = false,
    onProductClicked: () -> Unit,
    onChangeCartState: () -> Unit,
) {
    var verticalOffset by remember { mutableStateOf(0) }
    var internalBoxSize by remember { mutableStateOf(Size(0f, 0f)) }
    var defaultStageSize by remember { mutableStateOf(Size(width = 0f, height = 0f)) }
    var productStageSize by remember { mutableStateOf(defaultStageSize) }
    val productImageRotation = if (onCart) -35f else 0f

    /**
     * To create the floating effect, we should configure verticalOffset & productStageWidth
     * to be updated each 0.2 seconds , using Handler.
     */
    val mainHandler = Handler(Looper.getMainLooper())
    val floatingEffectControllerCallback = remember {
        object : Runnable {
            override fun run() {
                /**
                 * Check what is the current offset, to see whether the product is at bottom or top?
                 * The stage width basically can have 2 widths depending on the offset of the product.
                 * If the verticalOffset is > 0 => product at bottom so stage width should larger.
                 * If the verticalOffset is < 0 => product at top so stage width should be smaller
                 */
                if (verticalOffset < 0) {
                    /**
                     * Currently product is at bottom, which is below 0,
                     * We should set the offset to default - which is zero - and stage size to it's maximum value
                     */
                    verticalOffset = 0
                    productStageSize = defaultStageSize
                } else {
                    /**
                     * Currently product is at top, which is above 0,
                     * We should increase the offset and stage size to it's minimum value - which is half of the original size.
                     */
                    verticalOffset = verticalOffset.minus(60)
                    productStageSize = defaultStageSize.times(0.5f)
                }
                mainHandler.postDelayed(this, 600)
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        mainHandler.post(floatingEffectControllerCallback)
    }

    /** We start by creating the internal box height animation */
    val animatedInternalBoxHeight by animateSizeAsState(
        targetValue = internalBoxSize,
        animationSpec = TweenSpec(
            durationMillis = 600,
            easing = LinearEasing,
        )
    )

    /** We should now create the product's float animation */
    val animatedVerticalOffset by animateIntAsState(
        targetValue = verticalOffset,
        animationSpec = TweenSpec(
            durationMillis = 600,
            easing = LinearEasing,
        )
    )

    /** We should now create the product's extending & shrinking animation */
    val animatedStageSize by animateSizeAsState(
        targetValue = productStageSize,
        animationSpec = TweenSpec(
            durationMillis = 600,
            easing = LinearEasing,
        )
    )

    val animatedRotation by animateFloatAsState(
        targetValue = productImageRotation,
        animationSpec = TweenSpec(
            durationMillis = 600,
            easing = LinearEasing,
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .clickable { onProductClicked() },
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.large)
        ) {
            /** This is the most important step amigos, initiating the sizes */
            val fullBoxWidthAsFloat = this.constraints.maxWidth.toFloat()
            val fullBoxHeightAsFloat = this.constraints.maxHeight.toFloat()
            internalBoxSize = Size(
                width = fullBoxWidthAsFloat,
                height = fullBoxHeightAsFloat.div(2),
            )
            /** The default stage width ~ 4/5 of full grid item's width and height ~ 1/3 of that width to get an oval shape */
            val stageWidthAsFloat = fullBoxWidthAsFloat.times(0.8f)
            val stageHeightASFloat = stageWidthAsFloat.times(0.3f)
            defaultStageSize = Size(
                width = stageWidthAsFloat,
                height = stageHeightASFloat,
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(animatedInternalBoxHeight.height.getDp())
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.primary),
            )
            /**  Now we should create the stage - oval shape - that the product is placed on. */

            Canvas(
                modifier = Modifier
                    .width(animatedStageSize.width.getDp())
                    .height(animatedStageSize.height.getDp())
                    .align(Alignment.BottomCenter)
            ) {
                /** The oval shape - our stage - width is equal to canvas width and height equal to 2/3 of it's own width */
                drawOval(
                    color = DarkBlue,
                    size = Size(
                        width = this.size.width,
                        height = this.size.height,
                    ),
                )
            }
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .offset(
                        x = 0.getDp(), // we are not interested on x offset so it's always 0
                        y = animatedVerticalOffset.getDp()
                    )
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
                    .rotate(animatedRotation),
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            /** Product's interactions */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$$price",
                    style = MaterialTheme.typography.body1,
                )
                ReactiveCartIcon(
                    isOnCart = onCart,
                    onCartChange = onChangeCartState,
                )
            }
            /** Product's name */
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
            )
        }

    }
}