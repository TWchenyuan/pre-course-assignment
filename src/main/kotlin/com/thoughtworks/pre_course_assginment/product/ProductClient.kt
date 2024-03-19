package com.thoughtworks.pre_course_assginment.product

class ProductClient {
    fun listProducts(): List<ProductDTO> {
        return emptyList()
    }

}

data class ProductDTO(
    private val name: String
)


