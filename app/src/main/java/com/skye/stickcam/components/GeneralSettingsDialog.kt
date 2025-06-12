package com.skye.stickcam.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun GeneralSettingsDialog(
    switch1Name: String,
    switch2Name: String,
    switch1Value: Boolean,
    switch2Value: Boolean,
    onSwitch1ValueChange: (Boolean) -> Unit,
    onSwitch2ValueChange: (Boolean) -> Unit,
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
                color = Color(
                    0xCBDEDEDE
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .aspectRatio(1f)
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
                )
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                Switch(
                    checked = switch2Value,
                    onCheckedChange = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onSwitch2ValueChange(it)
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
                    text = "Background Color",
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
