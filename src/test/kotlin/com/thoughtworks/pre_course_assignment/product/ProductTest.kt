package com.thoughtworks.pre_course_assignment.product

import com.thoughtworks.pre_course_assginment.product.ProductClient
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProductTest {
    private val mockedClient = mockk<ProductClient>()

    @Test
    fun `should get empty product list from remote`() {
        every { mockedClient.listProducts() } returns emptyList()

        val products = mockedClient.listProducts()

        assertEquals(0, products.size)
    }
}