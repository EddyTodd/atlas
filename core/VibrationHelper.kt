package com.ynmidk.atlas.core

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission

enum class VibrationType {
    LIGHT,
    MEDIUM,
    STRONG_SHORT,
    STRONG_MEDIUM,
    MEDIUM_DOUBLE,
}

class VibrationHelper(context: Context) {
    private val vibrator: Vibrator? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.applicationContext
                .getSystemService(VibratorManager::class.java)
                ?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(type: VibrationType) {
        val vib = vibrator ?: return
        if (!vib.hasVibrator()) return

        val effect = when (type) {
            VibrationType.LIGHT -> VibrationEffect.createOneShot(16, 20)
            VibrationType.MEDIUM -> VibrationEffect.createOneShot(16, 40)
            VibrationType.STRONG_SHORT -> VibrationEffect.createOneShot(32, 80)
            VibrationType.STRONG_MEDIUM -> VibrationEffect.createOneShot(160, 200)
            VibrationType.MEDIUM_DOUBLE -> VibrationEffect.createWaveform(
                longArrayOf(0, 40, 60, 40),
                intArrayOf(0, 120, 0, 100),
                -1,
            )
        }
        vib.vibrate(effect)
    }
}
