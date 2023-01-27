package com.example.navigationsample.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class CountDownTimer(
    millisLeft: Long,
    private val countDownInterval: Long = 1000
) {
    private var timer: android.os.CountDownTimer? = null
    private val _onTickFormatted = MutableLiveData("")
    var millisLeft: Long = millisLeft
        private set

    fun start(owner: LifecycleOwner, observer: Observer<String>) {
        timer?.cancel()
        timer = object : android.os.CountDownTimer(millisLeft, countDownInterval) {
            override fun onFinish() {
            }

            override fun onTick(millisLeft: Long) {
                this@CountDownTimer.millisLeft = millisLeft
                _onTickFormatted.value = formatToMinutesAndSeconds(millisLeft)
            }
        }.start()
        _onTickFormatted.observe(owner, observer)
    }

    fun stop() {
        timer?.cancel()
    }

    private fun formatToMinutesAndSeconds(millis: Long): String {
        return if (millis < 1) "00"
        else format(millis / 60000) + ":" +
                format(millis % 60000 / 1000).take(2)
    }

    private fun format(time: Long): String {
        return if (time < 10) "0$time" else time.toString()
    }
}