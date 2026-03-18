# MagnetoMap Pro - Science Notes

## Magnetometry
The app uses the device's built-in magnetometer (`TYPE_MAGNETIC_FIELD`) to measure the ambient magnetic field in microteslas (µT), converted to nanoteslas (nT) internally. The raw vector is rotated into the world frame (North-East-Down) using a Madgwick AHRS filter fusing accelerometer and gyroscope data.

## Ordinary Kriging
To create a continuous 3D volumetric map from scattered point samples, the app employs Ordinary Kriging. 
1. **Variogram:** A spherical variogram model is used to quantify spatial autocorrelation.
2. **Interpolation:** For each voxel in the grid, a system of linear equations (Kriging matrix) is solved using LU decomposition to find the optimal weights for the surrounding samples.

## Anomaly Detection
Anomalies are detected by finding local maxima in the interpolated field that exceed a user-defined threshold. Features such as peak magnitude, spatial extent, and gradient are extracted and fed into a TensorFlow Lite neural network to classify the anomaly (e.g., rebar, pipe, AC wire).
