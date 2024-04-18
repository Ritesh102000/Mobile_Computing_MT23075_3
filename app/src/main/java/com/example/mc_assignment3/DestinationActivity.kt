package com.example.mc_assignment3


import android.graphics.Paint
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp


import com.example.mc_assignment3.DB.MyDataSource
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch


class DestinationActivity : ComponentActivity() {
    private lateinit var dataSource: MyDataSource
    private val sensorData: MutableState<List<SensorData>?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = MyDataSource(this)

        lifecycleScope.launch {
            val data = dataSource.getAllData()
            Log.d("DestinationActivity", "Fetched data: $data")
            sensorData.value = data
        }

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                if (sensorData.value == null) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    val data = sensorData.value!!

                    Column {
                        Text(text = "X vs Time")
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)){
                            SensorDataGraphX(sensorData = data)

                        }
                        Text(text = "Y vs Time")
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)){
                            SensorDataGraphY(sensorData = data)

                        }
                        Text(text = "Z vs Time")
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)){
                            SensorDataGraphZ(sensorData = data)

                        }
                    }
                }
            }
        }
    }



    @Composable
    fun SensorDataGraphX(sensorData: List<SensorData>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Red // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 150
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Y values",
                padding + 10f, // Adjust position
                if (yMax > 0) centerY + 30f else centerY - 30f, // Adjust based on positive/negative range
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )

            sensorData.forEach { data ->
                val x = padding + step * dataStepX
                val y = if (data.y >= 0) centerY - data.x * dataStepY else centerY + Math.abs(data.y) * dataStepY
                step += 1
                drawCircle(color = Color.Blue, radius = 1.dp.toPx(), center = Offset(x, y))
            }
        }
    }

    @Composable
    fun SensorDataGraphY(sensorData: List<SensorData>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Red // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 150
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Y values",
                padding + 10f, // Adjust position
                if (yMax > 0) centerY + 30f else centerY - 30f, // Adjust based on positive/negative range
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )

            sensorData.forEach { data ->
                val x = padding + step * dataStepX
                val y = if (data.y >= 0) centerY - data.y * dataStepY else centerY + Math.abs(data.y) * dataStepY
                step += 1
                drawCircle(color = Color.Blue, radius = 1.dp.toPx(), center = Offset(x, y))
            }
        }
    }
    @Composable
    fun SensorDataGraphZ(sensorData: List<SensorData>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Red // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 150
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Y values",
                padding + 10f, // Adjust position
                if (yMax > 0) centerY + 30f else centerY - 30f, // Adjust based on positive/negative range
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )

            sensorData.forEach { data ->
                val x = padding + step * dataStepX
                val y = if (data.y >= 0) centerY - data.z * dataStepY else centerY + Math.abs(data.y) * dataStepY
                step += 1
                drawCircle(color = Color.Blue, radius = 1.dp.toPx(), center = Offset(x, y))
            }
        }
    }
}




