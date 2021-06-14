package com.denistuskenis.spyfall.ui

import android.widget.SeekBar

fun OnSeekBarProgressChangeListener(
    onProgressChanged: (progress: Int) -> Unit
): SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        onProgressChanged(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
