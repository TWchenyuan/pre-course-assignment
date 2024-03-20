package com.thoughtworks.pre_course_assignment.product

class ProductService(private val client: ProductClient) {
    suspend fun listProducts(): List<Product> {
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
        this.image,
        inventories.map { Product.Inventory(it.zone, it.quantity) }
    )
}
