package com.thoughtworks.pre_course_assignment.product

import java.math.BigDecimal

class ProductService(private val client: ProductClient) {
    fun listProducts(): List<Product> {
        val products = client.listProducts()
        val inventoryHash = client.listInventories().groupBy { it.sku }
        return products.map { buildProduct(it, inventoryHash) }
    }

    private fun buildProduct(product: ProductDTO, inventoryHash: Map<String, List<InventoryDTO>>): Product {
        return product.toProduct(inventoryHash.getOrDefault(product.sku, emptyList()))
    }
}

fun ProductDTO.toProduct(inventories: List<InventoryDTO>): Product {
    return Product(
        this.sku,
        this.name,
        this.price,
        inventories.map { Product.Inventory(it.zone, it.quantity) }
    )
}

data class Product(
    val sku: String,
    val name: String,
    val price: BigDecimal,
    private val inventories: List<Inventory>,
) {

    val quantity: Long = inventories.sumOf { it.quantity }

    data class Inventory(
        val zone: String,
        val quantity: Long
    )
}