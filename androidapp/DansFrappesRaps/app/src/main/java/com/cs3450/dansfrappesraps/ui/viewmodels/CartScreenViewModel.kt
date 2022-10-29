package com.cs3450.dansfrappesraps.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cs3450.dansfrappesraps.ui.models.Cart
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.cs3450.dansfrappesraps.ui.repositories.CartRepository
import com.cs3450.dansfrappesraps.ui.repositories.UserRepository

class CartScreenState{
    var priceSum by mutableStateOf(0.00)
    var userId by mutableStateOf("")
    var name by mutableStateOf("")
    var _ingredients = mutableStateListOf<Ingredient>()
    val ingredients: List<Ingredient> get() = _ingredients
    var _frappuccinos = mutableStateListOf<Drink>()
    val frappuccinos: List<Drink> get() = _frappuccinos
    var arrivalTime by mutableStateOf("")
    var checkBalance by mutableStateOf(false)
    var checkCart by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var cart by mutableStateOf(Cart())
    var errorMessage by mutableStateOf("")
    var checkoutSuccess by mutableStateOf(false)
    var cartDeletion by mutableStateOf(false)

    fun addDrink(drink:Drink){
        _frappuccinos.add(drink)
    }
    fun addIngredients(ingredient: Ingredient){
        _ingredients.add(ingredient)
    }
    fun clearDrinks(){
        _frappuccinos.clear()
    }
    fun clearIngredients(){
        _ingredients.clear()
    }
}

class CartScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CartScreenState()

    fun deletingCart(){
        uiState.priceSum = 0.00
        uiState.clearDrinks()
        uiState.clearIngredients()
        uiState.checkBalance = false
        uiState.checkCart = false
        uiState.errorMessage = ""
    }
    suspend fun setupScreen(){
        getCart()
        checkCart()
        val drinks = getDrinks()
        for(drink in drinks){
            getIngredients(drink)
        }
        calculateBalance()
    }
    suspend fun getCart(): Cart? {
        if(CartRepository.getCart() != null) {
            uiState.cart = CartRepository.getCart()!!
        }
        else{uiState.cart = Cart()}
        return uiState.cart
    }
    suspend fun getDrinks(): List<Drink>{
        var drinks = uiState.cart.drinks
        if (drinks != null) {
            for(drink: Drink in drinks){
                if(!uiState.frappuccinos.contains(drink)){
                    uiState.addDrink(drink)
                }
            }
        }
        return uiState.frappuccinos
    }
    fun getIngredients(drink: Drink): List<Ingredient>{
        for(ingredients in drink.ingredients!!){
            if(!uiState.ingredients.contains(ingredients)) {
                uiState.addIngredients(ingredients)
            }
        }
        return uiState.ingredients
    }
    fun balanceCheck(): Boolean{
        val userBalance = UserRepository.userBalance()
        if (userBalance != null) {
            uiState.checkBalance = userBalance >= uiState.priceSum
            return uiState.checkBalance
        }
        else{
            uiState.checkBalance = false
            return uiState.checkBalance
        }
    }
    fun calculateBalance(): Double{
        uiState.loading = true
        for(ingredient in uiState.ingredients){
            uiState.priceSum += ingredient.inventory!!.PPU!!
        }
        uiState.loading = false
        return uiState.priceSum
    }
    suspend fun checkCart(){
        getDrinks()
        uiState.checkCart = uiState.frappuccinos.isNotEmpty()
    }
    fun checkInventory(){

    }
    fun makeOrder(){

    }
    suspend fun checkout(){
        checkInventory()
        if(balanceCheck()){
            makeOrder()
            CartRepository.deleteCart(uiState.cart)
            deletingCart()
            uiState.cart = Cart()
            uiState.checkoutSuccess = true
            uiState.cartDeletion = true
        }
        else{
            uiState.errorMessage = "There is not enough money in your account, please add money and try again."
        }
    }
}