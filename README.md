# 🎥 StickCam - Pose Detection & Visualizer Android App

StickCam is an Android application that visualizes human poses in real-time using stick figures, outlines, and raw camera feed. Built with Jetpack Compose, CameraX, and pose estimation models — it provides an intuitive interface for switching between modes and visualizing detected poses.

> 📌 **Note:** StickCam works best when the full body is visible in the camera view. The app allows full customization of colors, line widths, visibility of keypoints, numbers, and background color for an enhanced user experience.

---

## ✨ Features

* 📷 **Capture Photos** – Take snapshots while viewing live pose visualizations

* 📸 **Camera Mode** – See the raw camera feed

* 🦴 **Stick Mode** – Visualize poses using stick figures

* ✏️ **Outline Mode** – Show keypoints and body outlines with labels

* 📱 Built using **Jetpack Compose**

* 🧠 Real-time pose detection

* 🎮 Haptic feedback for button actions

---

## 🚀 Screenshots
---

## 📦 Installation

> ⚠️ **Permissions Required:** StickCam uses the device's camera hardware and requires **Camera permissions** to function properly. Please ensure permissions are granted when prompted.

1. Clone the repo:

   ```bash
   git clone https://github.com/yourusername/StickCam.git
   ```
2. Open it in Android Studio.
3. Build and run on a real device (Camera & ML models require actual hardware).

---

## 📲 Download

You can download the latest APK here:

👉 [Download StickCam APK](https://github.com/yourusername/StickCam/releases/latest)

---

## 🛠️ Built With

This app uses the **MoveNet SinglePose** model from [TensorFlow Hub](https://www.tensorflow.org/hub) with **TensorFlow Lite** for fast and accurate real-time pose detection. The model is open-source and licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).

* Kotlin
* Jetpack Compose
* CameraX
* TensorFlow Lite with MoveNet SinglePose model
* Android Haptic Feedback
* ViewModel & Clean Architecture principles

---

## 🧾 License

This project is licensed under the **Creative Commons Attribution-NonCommercial 4.0 International License**.
You can view it [here](https://creativecommons.org/licenses/by-nc/4.0/).

> 🚫 **Commercial use is not allowed**.

---

## 🤝 Contributing

Contributions are welcome! If you find bugs or want to suggest features:

* Open an issue
* Fork the repo
* Submit a PR

---

## 🙋‍♂️ Author

**Kai**
Studying CSE, passionate about app development, quantum computing, and AI/ML.

---

## 📣 Acknowledgments

* [MoveNet SinglePose](https://www.tensorflow.org/lite/models/pose_estimation/overview) by Google, distributed via TensorFlow Hub under Apache 2.0 License
* Jetpack Compose team for a great UI toolkit
