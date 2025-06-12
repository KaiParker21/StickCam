package com.skye.stickcam.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun StepsSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int
) {

    var sliderValue = remember { mutableFloatStateOf(value) }
    var haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "%.0f".format(sliderValue.floatValue),
            color = Color.Black
        )
        Slider(
            value = sliderValue.floatValue,
            onValueChange = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                sliderValue.floatValue = it
                onValueChange(it)
            },
            steps = steps,
            valueRange = valueRange
        )
    }
}