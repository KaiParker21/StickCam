package com.skye.stickcam.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PoseViewModel : ViewModel() {

    private val _keypoints = MutableStateFlow<List<Pair<Float, Float>>>(emptyList())
    val keypoints: StateFlow<List<Pair<Float, Float>>> =_keypoints

    private val _stickWidth = MutableStateFlow(7f)
    val stickWidth: StateFlow<Float> = _stickWidth

    private val _showNumbers = MutableStateFlow(false)
    val showNumbers: StateFlow<Boolean> = _showNumbers

    private val _showPoints = MutableStateFlow(false)
    val showPoints: StateFlow<Boolean> = _showPoints

    private val _showBackground = MutableStateFlow(false)
    val showBackground: StateFlow<Boolean> = _showBackground

    private val _backgroundColor = MutableStateFlow(android.graphics.Color.WHITE)
    val backgroundColor: StateFlow<Int> = _backgroundColor

    private val _stickColor = MutableStateFlow(android.graphics.Color.BLACK)
    val stickColor: StateFlow<Int> = _stickColor

    private val _stickAlpha = MutableStateFlow(255f)
    val stickAlpha: StateFlow<Float> = _stickAlpha

    private val _pointColor = MutableStateFlow(android.graphics.Color.RED)
    val pointColor: StateFlow<Int> = _pointColor

    private val _pointAlpha = MutableStateFlow(255f)
    val pointAlpha: StateFlow<Float> = _pointAlpha

    private val _pointRadius = MutableStateFlow(12f)
    val pointRadius: StateFlow<Float> = _pointRadius

    private val _isFrontCamera = MutableStateFlow(false)
    val isFrontCamera: StateFlow<Boolean> = _isFrontCamera

    private val _showStick = MutableStateFlow(false)
    val showStick: StateFlow<Boolean> = _showStick

    private val _connections = MutableStateFlow(
        listOf(
            5 to 6,
            5 to 7, 7 to 9,
            6 to 8, 8 to 10,
            5 to 11, 6 to 12,
            11 to 12,
            11 to 13, 13 to 15,
            12 to 14, 14 to 16
        )
    )
    val connections: StateFlow<List<Pair<Int, Int>>> = _connections
    
    var previousKeypoints: List<Pair<Float, Float>>? = null

    fun setShowStick(value: Boolean) {
        _showStick.value = value
    }

    fun setShowPoints(value: Boolean) {
        _showPoints.value = value
    }

    fun setStickColor(color: Int) {
        _stickColor.value = color
    }

    fun setStickAlpha(alpha: Float) {
        _stickAlpha.value = alpha
    }

    fun setStickWidth(width: Float) {
        _stickWidth.value = width
    }

    fun setPointRadius(radius: Float) {
        _pointRadius.value = radius
    }

    fun setPointColor(color: Int) {
        _pointColor.value = color
    }

    fun setPointAlpha(alpha: Float) {
        _pointAlpha.value = alpha
    }

    fun setShowNumbers(value: Boolean) {
        _showNumbers.value = value
    }

    fun setIsFrontCamera(value: Boolean) {
        _isFrontCamera.value = value
    }

    fun setShowBackground(value: Boolean) {
        _showBackground.value = value
    }

    fun setBackgroundColor(color: Int) {
        _backgroundColor.value = color
    }

    fun updateKeypoints(newPoints: List<Pair<Float, Float>>) {
        val smoothed = smoothKeypoints(newPoints, previousKeypoints)
        if (areKeypointsDifferent(smoothed, previousKeypoints ?: emptyList())) {
            _keypoints.value = smoothed
            previousKeypoints = smoothed
        }
    }

    private fun smoothKeypoints(
        new: List<Pair<Float, Float>>,
        old: List<Pair<Float, Float>>?,
        alpha: Float = 0.7f
    ): List<Pair<Float, Float>> {
        if (old == null || old.size != new.size) return new
        return new.zip(old).map { (n, o) ->
            val x = alpha * n.first + (1 - alpha) * o.first
            val y = alpha * n.second + (1 - alpha) * o.second
            Pair(x, y)
        }
    }

    private fun areKeypointsDifferent(
        a: List<Pair<Float, Float>>,
        b: List<Pair<Float, Float>>,
        threshold: Float = 2f
    ): Boolean {
        if (a.size != b.size) return true
        return a.zip(b).any { (p1, p2) ->
            kotlin.math.abs(p1.first - p2.first) > threshold ||
                    kotlin.math.abs(p1.second - p2.second) > threshold
        }
    }
}
