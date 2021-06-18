package com.denistuskenis.spyfall.ui.errors

import androidx.fragment.app.Fragment
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.functional.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private var isErrorCurrentlyShowing = false

fun <T> Fragment.handleWithDefaultErrorHandler(
    result: Result<*, T>,
    onSuccess: (T) -> Unit = {},
    onError: () -> Unit = {},
) {
    when (result) {
        is Result.Success -> onSuccess(result.value)
        is Result.Error -> {
            if (!isErrorCurrentlyShowing) {
                isErrorCurrentlyShowing = true
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.default_error_dialog_title)
                    .setMessage(R.string.default_error_dialog_message)
                    .setPositiveButton(R.string.default_error_dialog_button) { _, _ -> }
                    .setOnDismissListener { isErrorCurrentlyShowing = false }
                    .setOnCancelListener { isErrorCurrentlyShowing = false }
                    .create()
                    .show()
            }
            onError()
        }
    }
}
