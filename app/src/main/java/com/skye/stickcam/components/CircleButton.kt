package com.skye.stickcam.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skye.stickcam.ui.theme.StickCamTheme
import kotlinx.coroutines.delay

@Composable
fun CircleButton(
    onClick: () -> Unit,
    heightFraction: Float,
    painter: Painter? = null,
    color: Color = Color.Transparent,
    icon: ImageVector? = null
) {

    var isPressed by remember { mutableStateOf(false) }

    val animatedPadding by animateDpAsState(
        targetValue = if (isPressed) 26.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        ),
        label = "paddingAnim"
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }

    ElevatedCard(
        shape = CircleShape,
        modifier = Modifier
            .fillMaxHeight(heightFraction)
            .aspectRatio(1f)
            .clickable(
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
            .border(2.dp, Color.White, CircleShape)
            .background(Color.Transparent, CircleShape)
            .padding(animatedPadding),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            painter?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.8f),
                    tint = Color.White
                )
            }
        }
    }
}


@Preview
@Composable
fun CircleButtonPreview() {
    StickCamTheme {
        CircleButton(
            onClick = {

            },
            color = Color.White,
            heightFraction = 0.8f
        )
    }
}