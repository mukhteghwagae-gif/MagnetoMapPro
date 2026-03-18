package com.magnetomap.pro.ml

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnomalyClassifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var interpreter: Interpreter? = null

    init {
        try {
            interpreter = TFLiteModelLoader.loadModel(context, "models/anomaly_classifier.tflite")
        } catch (e: Exception) {
            // Model might not exist yet, fallback gracefully
        }
    }

    fun classify(features: FloatArray): Pair<Int, Float> {
        if (interpreter == null) return Pair(5, 0.0f) // Unknown
        
        val input = arrayOf(features)
        val output = arrayOf(FloatArray(6))
        
        try {
            interpreter?.run(input, output)
        } catch (e: Exception) {
            return Pair(5, 0.0f)
        }

        val probabilities = output[0]
        var maxIdx = 0
        var maxProb = probabilities[0]
        for (i in 1 until 6) {
            if (probabilities[i] > maxProb) {
                maxProb = probabilities[i]
                maxIdx = i
            }
        }
        return Pair(maxIdx, maxProb)
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }
}
