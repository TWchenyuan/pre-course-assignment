package com.thoughtworks.pre_course_assignment.product

import java.math.BigDecimal

data class Product(
    val sku: String,
    val name: String,
    val type: ProductType,
    val originalPrice: BigDecimal,
    val image: String,
    private val inventories: List<Inventory>,
) {

    val quantity: Long = inventories.sumOf { it.quantity }
    val price: BigDecimal = when (this.type) {
        ProductType.NORMAL -> this.originalPrice
        ProductType.HIGH_DEMAND -> {
            when {
                this.quantity > 100 -> this.originalPrice
                this.quantity in 30..100 -> this.originalPrice.multiply(BigDecimal.valueOf(1.2))
                else -> this.originalPrice.multiply(BigDecimal.valueOf(1.5))
            }
        }
    }

    data class Inventory(
        val zone: String,
        val quantity: Long
    )
}
enum class ProductType {
    NORMAL, HIGH_DEMAND
}

fun List<Product>.toStringList(): List<List<String>> {
    return this.map {
        listOf(
            it.sku,
            it.name,
            it.type.name,
            it.price.toPlainString(),
            it.quantity.toString(),
            it.image
        )
    }
}
