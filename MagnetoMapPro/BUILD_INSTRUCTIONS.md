# MagnetoMap Pro - Build Instructions

## Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android NDK 25.2.9519653

## Steps to Build
1. Open Android Studio.
2. Select **File > Open** and choose the `MagnetoMapPro` directory.
3. Allow Gradle to sync. This will download all required dependencies including ARCore, Sceneform, Room, Hilt, and TensorFlow Lite.
4. Connect an Android device running Android 8.0 (API 26) or higher. For AR features, the device must support ARCore.
5. Click the **Run** button (green play icon) or run `./gradlew assembleDebug` from the terminal.
6. The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Notes
- **TFLite Model:** A placeholder model file is expected at `assets/models/anomaly_classifier.tflite`. You will need to replace this with a real trained model for accurate anomaly classification.
- **ARCore Support:** If the device does not support ARCore, the app will gracefully degrade to a 2D heatmap mode.
