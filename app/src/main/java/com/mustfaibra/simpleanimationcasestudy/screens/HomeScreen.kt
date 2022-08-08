package com.mustfaibra.simpleanimationcasestudy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mustfaibra.simpleanimationcasestudy.R
import com.mustfaibra.simpleanimationcasestudy.components.ProductItemLayout
import com.mustfaibra.simpleanimationcasestudy.components.SearchField
import com.mustfaibra.simpleanimationcasestudy.models.Product

@Composable
fun HomeScreen() {

    /** We just want a fake list of products */
    val allProducts = listOf(
        Product(
            id = 1,
            name = "Pegasus Trail Gortex Green",
            image = R.drawable.pegasus_trail_3_gore_tex_dark_green,
            price = 149.0,
        ),
        Product(
            id = 2,
            name = "Pegasus Trail Gortex Lemon",
            image = R.drawable.pegasus_trail_3_gore_tex_lemon,
            price = 155.0,
        ),
        Product(
            id = 3,
            name = "Air Huarache Gold",
            image = R.drawable.air_huarache_le_gold_black,
            price = 159.0,
        ),
        Product(
            id = 4,
            name = "Air Huarache Gray",
            image = R.drawable.air_huarache_le_gray_dark,
            price = 149.0,
        ),
        Product(
            id = 5,
            name = "Air Huarache Pink",
            image = R.drawable.air_huarache_le_pink_black,
            price = 125.0,
        ),
        Product(
            id = 6,
            name = "Air Huarache Red",
            image = R.drawable.air_huarache_le_red_black,
            price = 145.0,
        ),
        Product(
            id = 7,
            name = "Blazer Low Black",
            image = R.drawable.blazer_low_black,
            price = 120.0,
        ),
        Product(
            id = 8,
            name = "Dunk Slow Black",
            image = R.drawable.dunk_slow_black,
            price = 189.99,
        ),
        Product(
            id = 9,
            name = "Dunk Slow Pink",
            image = R.drawable.dunk_slow_pink,
            price = 170.0,
        )
    )
    val displayedProducts: MutableList<Product> = remember { mutableStateListOf() }
    val cartProductsIds: MutableList<Int> = remember { mutableStateListOf() }
    var searchQuery by remember { mutableStateOf("") }.also {
        /** Init the first filtered list, which is all product */
        displayedProducts.addAll(allProducts)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 15.dp),
    ) {
        /** Search field section */
        item(
            span = {
                GridItemSpan(2)
            }
        ) {
            SearchField(
                modifier = Modifier.padding(top = 36.dp),
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                textColor = MaterialTheme.colors.onSurface,
                value = searchQuery,
                placeholder = "Let's help you, type name...",
                onValueChange = {
                    searchQuery = it
                    /** Then filter the list */
                    displayedProducts.clear()
                    displayedProducts.addAll(
                        allProducts.filter { product ->
                            product.name.startsWith(searchQuery, true)
                        }
                    )
                },
                onFocusChange = {

                },
                onKeyboardActionClicked = {

                }
            )
        }
        items(displayedProducts, key = { it.id }) { product ->
            ProductItemLayout(
                modifier = Modifier.fillMaxWidth(),
                image = product.image,
                price = product.price,
                title = product.name,
                onCart = product.id in cartProductsIds,
                onProductClicked = {
                    /** What to do when the product is clicked */
                },
                onChangeCartState = {
                    /** Updating our cart state */
                    if (product.id in cartProductsIds) {
                        cartProductsIds.remove(product.id)
                    } else {
                        cartProductsIds.add(product.id)
                    }
                }
            )
        }
    }
}