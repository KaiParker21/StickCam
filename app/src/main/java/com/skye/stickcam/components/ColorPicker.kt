package com.skye.stickcam.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(
    ExperimentalMaterial3Api::class
)

@Composable
fun ColorPicker(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit
) {

    val controller = rememberColorPickerController()
    var selectedColor by remember { mutableStateOf(Color.White) }
    var haptic = LocalHapticFeedback.current

    BasicAlertDialog(
        onDismissRequest = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onDismissRequest()
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .background(
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AlphaTile(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                controller = controller
            )

            Spacer(modifier = Modifier.height(18.dp))

            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                controller = controller,
                onColorChanged = { color ->
                    selectedColor = color.color
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(vertical = 8.dp),
                controller = controller
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ElevatedButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = "Cancel"
                    )
                }

                ElevatedButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDismissRequest()
                        onColorSelected(selectedColor)
                    }
                ) {
                    Text(
                        text = "Done"
                    )
                }
            }
        }
    }
}