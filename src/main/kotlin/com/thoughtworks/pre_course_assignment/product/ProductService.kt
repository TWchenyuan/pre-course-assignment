package com.thoughtworks.pre_course_assignment.product

class ProductService(private val client: ProductClient) {
    fun listProducts(): List<Product> {
        val products = client.listProducts()
        val inventories = client.listInventories()
        val productInventoryHash = inventories.groupBy { it.sku }
        return products.map {
            Product(it.sku, it.name, productInventoryHash.get(it.sku)?.map { it.quantity }?.sum() ?: 0)
        }
    }
}

data class Product(
    val sku: String,
    val name: String,
    val quantity: Long
)

