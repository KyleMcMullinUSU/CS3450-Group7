package com.cs3450.dansfrappesraps.ui.models

data class Drink (
    val id: String? = null,
    val name: String? = null,
    val ingredients: MutableList<Inventory>? = null,
    )