package com.denistuskenis.spyfall.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.denistuskenis.spyfall.ui.ViewBindingInflater

class ReusableListAdapter<Data: Any, Binding: ViewBinding>(
    private val bindData: (Data, Binding) -> Unit,
    private val inflateBinding: ViewBindingInflater<Binding>,
    areItemsTheSame: (firstItem: Data, secondItem: Data) -> Boolean = Any::equals,
    areContentsTheSame: (firstItem: Data, secondItem: Data) -> Boolean = areItemsTheSame
): ListAdapter<Data, ReusableListAdapter.ViewBindingHolder<Binding>>(
    object: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return areContentsTheSame(oldItem, newItem)
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<Binding> {
        return ViewBindingHolder(inflateBinding(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewBindingHolder<Binding>, position: Int) {
        val data = getItem(position)
        bindData(data, holder.binding)
    }

    class ViewBindingHolder<Binding: ViewBinding>(
        val binding: Binding
    ): RecyclerView.ViewHolder(binding.root)
}
