package com.denistuskenis.spyfall.ui.destinations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.denistuskenis.spyfall.ui.ViewBindingInflater

abstract class DestinationFragment<Binding: ViewBinding>(
    private val inflateBinding: ViewBindingInflater<Binding>,
) : Fragment() {

    private var _binding: Binding? = null
    private var _navController: NavController? = null

    protected val binding: Binding get() =
        _binding ?: throw IllegalStateException(
            "Binding cannot be called after view is destroyed."
        )

    protected val navController: NavController get() =
        _navController ?: throw IllegalStateException(
            "It is incorrect to call navigation controller after the view is destroyed."
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflateBinding(inflater, container, false)
        _binding = binding
        _navController = findNavController()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _navController = null
    }
}
