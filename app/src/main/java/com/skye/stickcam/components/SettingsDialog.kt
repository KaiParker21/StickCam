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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun SettingsDialog(
    slider1Name: String,
    slider2Name: String,
    slider1Value: Float,
    slider2Value: Float,
    onSlider1ValueChange: (Float) -> Unit,
    onSlider2ValueChange: (Float) -> Unit,
    switch1Name: String,
    switch2Name: String,
    switch1Value: Boolean,
    onSwitch1ValueChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit
) {

    var showColorPicker by remember { mutableStateOf(false) }
    var haptic = LocalHapticFeedback.current

    BasicAlertDialog(
        onDismissRequest = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onDismissRequest()
        },
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(20.dp),
                color = Color(
                    0xCBDEDEDE
                )
            )
            .fillMaxWidth()
            .aspectRatio(0.8f)
    ) {

        if(showColorPicker) {
            ColorPicker(
                onDismissRequest = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showColorPicker = false
                },
                onColorSelected = { color->
                    onColorSelected(color)
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = slider1Name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Black
            )
            StepsSlider(
                value = slider1Value,
                onValueChange = {
                    onSlider1ValueChange(it)
                },
                steps = 50,
                valueRange = 0f..49f
            )
            Text(
                text = slider2Name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Black
            )
            StepsSlider(
                value = slider2Value,
                onValueChange = {
                    onSlider2ValueChange(it)
                },
                steps = 256,
                valueRange = 0f..255f
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = switch1Name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Switch(
                    checked = switch1Value,
                    onCheckedChange = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onSwitch1ValueChange(it)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = switch2Name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                IconButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showColorPicker = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = "Color Picker",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}