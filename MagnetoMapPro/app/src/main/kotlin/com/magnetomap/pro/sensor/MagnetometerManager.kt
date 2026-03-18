package com.magnetomap.pro.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.magnetomap.pro.domain.model.MagneticSample
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sqrt

@Singleton
class MagnetometerManager @Inject constructor(
    @ApplicationContext private val context: Context
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val fusionFilter = SensorFusionFilter()
    
    private val _magneticFlow = MutableStateFlow<MagneticSample?>(null)
    val magneticFlow: StateFlow<MagneticSample?> = _magneticFlow.asStateFlow()

    private var lastTimestamp = 0L
    private var ax = 0f; private var ay = 0f; private var az = 0f
    private var gx = 0f; private var gy = 0f; private var gz = 0f

    private var kx = 0f; private var ky = 0f; private var kz = 0f
    private var px = 1f; private var py = 1f; private var pz = 1f
    private val q = 0.01f
    private val r = 0.1f

    private var biasX = 0f; private var biasY = 0f; private var biasZ = 0f

    fun start() {
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                ax = event.values[0]; ay = event.values[1]; az = event.values[2]
            }
            Sensor.TYPE_GYROSCOPE -> {
                gx = event.values[0]; gy = event.values[1]; gz = event.values[2]
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                val dt = if (lastTimestamp == 0L) 0.02f else (event.timestamp - lastTimestamp) * 1e-9f
                lastTimestamp = event.timestamp

                fusionFilter.update(gx, gy, gz, ax, ay, az, dt)

                val mx = event.values[0] - biasX
                val my = event.values[1] - biasY
                val mz = event.values[2] - biasZ

                val worldMag = fusionFilter.rotateVector(mx, my, mz)

                px += q; py += q; pz += q
                val kGainX = px / (px + r)
                val kGainY = py / (py + r)
                val kGainZ = pz / (pz + r)
                
                kx += kGainX * (worldMag[0] - kx)
                ky += kGainY * (worldMag[1] - ky)
                kz += kGainZ * (worldMag[2] - kz)
                
                px *= (1 - kGainX)
                py *= (1 - kGainY)
                pz *= (1 - kGainZ)

                val magnitude = sqrt(kx * kx + ky * ky + kz * kz)
                val isContaminated = magnitude > 200f || magnitude < 20f

                _magneticFlow.value = MagneticSample(
                    x = 0f, y = 0f, z = 0f,
                    bx = kx, by = ky, bz = kz,
                    magnitude = magnitude,
                    timestamp = event.timestamp,
                    isContaminated = isContaminated
                )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
