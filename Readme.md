# Android Sensor Data Collection App

This Android application is designed to collect accelerometer sensor data and provide real-time display and export functionality. It leverages the accelerometer sensor available in Android devices to collect data and stores it in a local SQLite database for further analysis.

## Features:

1. **Sensor Data Collection:** The app continuously reads accelerometer data from the device's hardware sensor. It registers a sensor listener to capture the x, y, and z-axis values of the accelerometer. The collected data is then logged into a local SQLite database for storage and analysis.

2. **Real-time Display:** The application offers a real-time display of the accelerometer data on the user interface (UI). Users can view the current readings of the accelerometer on the screen as they move their device.

3. **Data Export:** It provides functionality to export the collected sensor data to a text file. This feature allows users to analyze the data offline or share it with other applications for further processing.

4. **Navigation:** The app includes navigation to another activity called `DestinationActivity` via a floating action button. This feature allows users to access additional functionalities or view detailed graphs of the collected sensor data.

## Components:

- **MainActivity:** This serves as the main entry point of the application. It initializes the sensor manager, registers sensor listeners, and handles the sensor data collection process. Additionally, it sets up the user interface using Jetpack Compose, Google's modern toolkit for building native Android UI.

- **DestinationActivity:** This activity is responsible for displaying graphs of the sensor data collected over time. It fetches the data from the SQLite database and utilizes Jetpack Compose to draw graphs for the X, Y, and Z axes, providing users with visual representations of the accelerometer data.

- **SensorDataDisplay Composable:** This is a Composable function responsible for rendering the real-time display of the accelerometer data on the UI. It dynamically updates as new data is received from the sensor, providing users with immediate feedback on the device's movements.

- **Predict Activity:** This section mentions a `Predict` activity, which appears to be unrelated to the sensor data collection app. It might be an artifact or reference to another project included in the document accidentally.

# Image Classifier using Jetpack Compose and TensorFlow Lite

This Android application demonstrates how to build a simple image classifier using Jetpack Compose for UI and TensorFlow Lite for machine learning inference.

## Features:
- Allows users to select an image from their device's gallery.
- Uses a pre-trained MobileNetV1 model for image classification.
- Displays the predicted label for the selected image.

## Usage:

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
4. Grant necessary permissions for sensor access if prompted.
5. The app will start collecting accelerometer data and displaying it on the UI.
6. Use the floating action button to navigate to the `DestinationActivity`.
7. Press the "B" button to export the collected data to a file.
8. Click on the "Select Image" button to choose an image from the gallery.
9. Click on the "Predict" button to classify the selected image.
10. The predicted label will be displayed below the buttons.

## Requirements:

- Android Studio
- Android SDK (API level 24 or higher)
- Kotlin programming language

## Notes:

- Ensure that the necessary permissions for sensor access are declared in the AndroidManifest.xml file.
- The code snippet provided lacks some details such as the implementation of `MyDataSource` and `DestinationActivity`, which are assumed to be part of the larger project.

# Mobile_Computing_MT23075_3
