package com.example.mc_assignment3

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mc_assignment3.DB.MyDataSource
import com.example.mc_assignment3.ui.theme.MC_Assignment3Theme
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var SersorManager: SensorManager
    private var accelerometerSensor: Sensor? = null
    private lateinit var dataSource: MyDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        dataSource = MyDataSource(this)
        super.onCreate(savedInstanceState)
        dataSource.dbHelper.writableDatabase
        SersorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = SersorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val fileName = "sensor_data.txt"

        setContent {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(this, DestinationActivity::class.java)
                            this.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            ) { innerpadding ->
                Column(Modifier.padding(innerpadding)) {
                    SensorDataDisplay()
                    Button(onClick = { dataSource.exportDataToFile(this@MainActivity,fileName)}) {
                        Text(text = "B")
                        val intent = Intent(this@MainActivity, Predict::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometerSensor?.let {
            SersorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        SersorManager.unregisterListener(sensorListener)
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor == accelerometerSensor) {
                val orientation = calculateOrientation(event.values)
                val currentTimeMillis = System.currentTimeMillis()
                dataSource.insertData(
                    currentTimeMillis,
                    orientation[0].toString(),
                    orientation[1].toString(),
                    orientation[2].toString()
                )
                orientationState.value = orientation.clone()
                Log.d("row", dataSource.getRowCount().toString())
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not needed for this example
        }
    }

    private fun calculateOrientation(accelerometerData: FloatArray): FloatArray {
        // Since accelerometerData already contains x, y, and z coordinates,
        Log.d("Orientation", accelerometerData[0].toString())
        return accelerometerData
    }

    @Composable
    fun SensorDataDisplay() {
        val orientation by remember { orientationState }

        LaunchedEffect(orientation) {
            Log.d("SensorDataDisplay", "Orientation changed: ${orientation.joinToString(", ")}")
        }

        Column {
            Text(text = "X: ${orientation[0]}")
            Text(text = "Y: ${orientation[1]}")
            Text(text = "Z: ${orientation[2]}")
        }
    }

    companion object {
        val orientationState = mutableStateOf(floatArrayOf(0f, 0f, 0f))
    }

}
