package com.denistuskenis.spyfall.ui.progress

import android.app.Activity
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

private const val DIALOG_TAG = "BLOCKING_PROGRESS_INDICATOR"

class BlockingProgressFragment : DialogFragment() {

    override fun onStop() {
        super.onStop()
        requireActivity().setTouchable(true)
    }
}

fun Fragment.showBlockingProgress() {
    if (childFragmentManager.findFragmentByTag(DIALOG_TAG) == null) {
        requireActivity().setTouchable(false)

        BlockingProgressFragment().apply {
            isCancelable = false
        }.showNow(childFragmentManager, DIALOG_TAG)
    }
}

fun Fragment.hideBlockingProgress() {
    (childFragmentManager.findFragmentByTag(DIALOG_TAG) as? DialogFragment)?.dismiss()
}

private fun Activity.setTouchable(isTouchable: Boolean) {
    with(window) {
        if (isTouchable) clearFlags(FLAG_NOT_TOUCHABLE) else addFlags(FLAG_NOT_TOUCHABLE)
    }
}
