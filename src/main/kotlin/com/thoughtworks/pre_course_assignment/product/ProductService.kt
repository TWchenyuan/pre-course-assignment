package com.thoughtworks.pre_course_assignment.product

class ProductService(private val client: ProductClient) {
    fun listProducts(): List<Product> {
        val products = client.listProducts()
        val inventories = client.listInventories()
        return products.map {
            Product(it.sku, it.name, inventories.first { inventory -> inventory.sku == it.sku }.quantity)
        }
    }
}

data class Product(
    val sku: String,
    val name: String,
    val quantity: Long
)

