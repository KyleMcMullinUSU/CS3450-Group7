package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.IngredientsRepository

class EditMenuState() {
    var name by mutableStateOf("")
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var loading by mutableStateOf(false)
}

class EditMenuViewModel(application: Application): AndroidViewModel(application) {
    var uiState = EditMenuState()

    fun setUpInitialState(id: String?) {
        if (id == null) return
//        val drink = uiState.ingredients?.find { it.id == id } ?: return
//        uiState.name = drink.name ?: ""
//        uiState.ingredients = drink.ingredients ?: mutableListOf()
    }

    fun addInventory() {
        uiState._ingredients.add(Ingredient())
    }

    fun incrementIngredient(ingredient: Ingredient) {
        uiState._ingredients[uiState._ingredients.indexOf(ingredient)] = ingredient.copy(count = ingredient.count?.plus(1))
//        uiState._ingredients.find(ingredient::equals)?.count?.plus(1)
    }

    fun decrementIngredient(ingredient: Ingredient) {
        if (uiState._ingredients.find(ingredient::equals)?.count!! > 0) {
            uiState._ingredients[uiState._ingredients.indexOf(ingredient)] = ingredient.copy(count = ingredient.count?.minus(1))
        }
    }

    suspend fun getIngredients() {
        uiState.loading = true
        uiState._ingredients.addAll(IngredientsRepository.getIngredients())
    }
}