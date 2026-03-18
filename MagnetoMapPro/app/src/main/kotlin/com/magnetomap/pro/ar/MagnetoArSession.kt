package com.magnetomap.pro.ar

import android.content.Context
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.gorisse.thomas.sceneform.ArSceneView
import com.magnetomap.pro.domain.model.MagneticSample

class MagnetoArSession(private val context: Context) {
    var arSceneView: ArSceneView? = null
    private var session: Session? = null

    fun setup(view: ArSceneView) {
        arSceneView = view
        session = Session(context)
        
        val config = Config(session)
        config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        if (session?.isDepthModeSupported(Config.DepthMode.AUTOMATIC) == true) {
            config.depthMode = Config.DepthMode.AUTOMATIC
        }
        session?.configure(config)
        arSceneView?.setupSession(session)
    }

    fun getCurrentPoseWithSample(sample: MagneticSample): MagneticSample {
        val frame = arSceneView?.arFrame ?: return sample
        val camera = frame.camera
        val pose = camera.pose
        
        return sample.copy(
            x = pose.tx(),
            y = pose.ty(),
            z = pose.tz()
        )
    }

    fun pause() {
        arSceneView?.pause()
    }

    fun resume() {
        arSceneView?.resume()
    }

    fun destroy() {
        arSceneView?.destroy()
    }
}
