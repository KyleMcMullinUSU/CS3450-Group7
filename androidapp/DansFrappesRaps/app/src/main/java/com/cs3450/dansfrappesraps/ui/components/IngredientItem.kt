package com.cs3450.dansfrappesraps.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs3450.dansfrappesraps.ui.models.Ingredient

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    // on Plus pressed is going to be state.list.item.count++
    // on Minus pressed is going to be state.list.item.count--
    onPlusPressed: () -> Unit = {},
    onMinusPressed: () -> Unit = {}
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Row {
            Button(
                onClick = onMinusPressed
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Minus")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(modifier = Modifier.padding(horizontal = 3.dp),
                text = "${ingredient.count}",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                onClick= onPlusPressed
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
            }
            Text(
                text = ingredient.inventory?.name ?: "",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )

        }
    }

}