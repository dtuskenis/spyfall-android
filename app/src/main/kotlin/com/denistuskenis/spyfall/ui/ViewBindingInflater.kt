package com.denistuskenis.spyfall.ui

import android.view.LayoutInflater
import android.view.ViewGroup

typealias ViewBindingInflater<Binding> = (
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
) -> Binding
