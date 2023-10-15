package com.denistuskenis.spyfall.ui.adapter

sealed class ListItem<out T : Any> {

    data object Placeholder : ListItem<Nothing>()

    data class Data<T : Any>(val location: T) : ListItem<T>()
}

fun <T : Any> List<T>.asListItems(): List<ListItem<T>> = map { ListItem.Data(it) }
