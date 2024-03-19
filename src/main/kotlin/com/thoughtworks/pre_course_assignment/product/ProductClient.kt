package com.thoughtworks.pre_course_assignment.product

class ProductClient {
    fun listProducts(): List<ProductDTO> {
        return emptyList()
    }
    fun listInventories(): List<InventoryDTO> {
        return emptyList()
    }
}

data class ProductDTO(
    val name: String,
    val sku: String,
)


data class InventoryDTO(
    val sku: String,
    val zone: String,
    val quantity: Long
)
