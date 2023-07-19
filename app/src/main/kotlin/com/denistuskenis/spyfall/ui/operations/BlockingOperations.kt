package com.denistuskenis.spyfall.ui.operations

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.denistuskenis.spyfall.functional.Result
import com.denistuskenis.spyfall.ui.errors.handleWithDefaultErrorHandler
import com.denistuskenis.spyfall.ui.progress.hideBlockingProgress
import com.denistuskenis.spyfall.ui.progress.showBlockingProgress
import kotlinx.coroutines.launch

fun <T> Fragment.performBlockingOperationWithDefaultErrorHandler(
    getResult: suspend () -> Result<*, T>,
    onSuccess: (T) -> Unit = {},
) {
    showBlockingProgress()
    lifecycleScope.launch {
        handleWithDefaultErrorHandler(getResult(), onSuccess)
    }.invokeOnCompletion { hideBlockingProgress() }
}
