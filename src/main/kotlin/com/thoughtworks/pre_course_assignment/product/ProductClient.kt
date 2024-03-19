package com.thoughtworks.pre_course_assignment.product

import java.math.BigDecimal

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
    val type: String,
    val price: BigDecimal,
    val image: String
)


data class InventoryDTO(
    val sku: String,
    val zone: String,
    val quantity: Long
)
