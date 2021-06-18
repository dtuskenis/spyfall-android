package com.denistuskenis.spyfall.ui

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import coil.imageLoader
import coil.request.ImageRequest

fun Fragment.loadImage(imageUrl: String, onSuccess: (Drawable) -> Unit) {
    val context = requireContext()

    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .lifecycle(lifecycle)
        .target(onSuccess = onSuccess)
        .build()

    context.imageLoader.enqueue(request)
}
