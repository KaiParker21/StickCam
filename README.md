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

Here are the screenshots showcasing the key features of the app:

<img src="https://private-user-images.githubusercontent.com/208963938/454364248-418a118a-77e4-4691-94c5-9f8fe2256268.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364247-d06884b8-000f-4012-b8c5-9a4048312781.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364250-096f2792-d278-46fc-9218-8072572f60ec.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364244-ea647161-5c16-4f89-b5dd-06ec5c40e7c6.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364251-512f2b3c-3039-471f-8de6-da5742c59a13.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364245-7eaa665d-4766-4048-9601-926b77d29739.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364237-ebcd5d19-15cd-4115-bca7-02c7d0a258ae.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364238-7c57c093-9482-41d4-8277-08267c555915.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364249-30d65b27-4582-4899-b81d-7a79811c36dc.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364246-1f3ddc2f-df64-4cd4-840e-9bce6b70bb5a.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364252-8ac31712-8434-4d79-8a5c-cce9fe48db4a.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364253-03d2d126-b505-4de2-91c3-3e83d39e0104.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364240-3d0c0219-e97d-4602-a351-45b0f6a8e51a.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364239-46d7c3bb-4c1e-4a1c-b4ee-395142ace127.jpg" width="300" />
<img src="https://private-user-images.githubusercontent.com/208963938/454364241-591a6874-f5c2-4296-9960-c81f27a9cc98.jpg" width="300" />

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
