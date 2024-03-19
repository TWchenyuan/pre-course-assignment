package com.thoughtworks.pre_course_assignment.product

import java.math.BigDecimal

class ProductService(private val client: ProductClient) {
    fun listProducts(): List<Product> {
        val products = client.listProducts()
        val inventoryHash = client.listInventories().groupBy { it.sku }
        return products.map { it.toProduct(inventoryHash[it.sku] ?: emptyList()) }
    }

}

fun ProductDTO.toProduct(inventories: List<InventoryDTO>): Product {
    return Product(
        this.sku,
        this.name,
        ProductType.valueOf(this.type),
        this.price,
        inventories.map { Product.Inventory(it.zone, it.quantity) }
    )
}

data class Product(
    val sku: String,
    val name: String,
    val type: ProductType,
    val originalPrice: BigDecimal,
    private val inventories: List<Inventory>,
) {

    val quantity: Long = inventories.sumOf { it.quantity }
    val price: BigDecimal = when (this.type) {
        ProductType.NORMAL -> this.originalPrice
        ProductType.HIGH_DEMAND -> {
            when {
                this.quantity > 100 -> this.originalPrice
                this.quantity in 30..100 -> this.originalPrice.multiply(BigDecimal.valueOf(1.2))
                else -> this.originalPrice
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
