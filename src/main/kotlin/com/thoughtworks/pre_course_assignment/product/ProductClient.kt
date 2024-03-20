package com.thoughtworks.pre_course_assignment.product

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import java.math.BigDecimal

interface ProductClient {

    @GET("/products")
    suspend fun listProducts(): List<ProductDTO>

    @GET("/inventories")
    suspend fun listInventories(): List<InventoryDTO>
}

data class ProductDTO(
    val name: String,
    @SerializedName("SKU")
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
