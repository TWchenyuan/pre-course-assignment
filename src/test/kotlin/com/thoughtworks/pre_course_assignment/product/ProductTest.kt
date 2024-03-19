package com.thoughtworks.pre_course_assignment.product

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProductTest {
    private val mockedClient = mockk<ProductClient>()
    private val productService = ProductService(mockedClient)

    @Test
    fun `should get empty product list from remote`() {
        every { mockedClient.listProducts() } returns emptyList()

        val products = mockedClient.listProducts()

        assertEquals(0, products.size)
    }

    @Test
    fun `should get inventory when given a single region`() {
        val PRODUCT_1 = ProductDTO("kotlin", "SKU_1")
        val INVENTORY_1 = InventoryDTO("SKU_1", "CHINA", 1000)
        every { mockedClient.listProducts() } returns listOf(PRODUCT_1)
        every { mockedClient.listInventories() } returns listOf(INVENTORY_1)

        val products = this.productService.listProducts()

        assertEquals(1, products.size)
        assertEquals("SKU_1", products[0].sku)
        assertEquals("kotlin", products[0].name)
        assertEquals(1000, products[0].quantity)
    }

    @Test
    fun `should get 0 quantity when given an empty inventory`() {
        val PRODUCT_1 = ProductDTO("kotlin", "SKU_1")
        every { mockedClient.listProducts() } returns listOf(PRODUCT_1)
        every { mockedClient.listInventories() } returns emptyList()

        val products = this.productService.listProducts()

        assertEquals(1, products.size)
        assertEquals("SKU_1", products[0].sku)
        assertEquals("kotlin", products[0].name)
        assertEquals(0, products[0].quantity)
    }

    @Test
    fun `should be 159 entities in total when 150 in region A and 9 in region B`() {
        val PRODUCT_1 = ProductDTO("kotlin", "SKU_1")
        val INVENTORIES = listOf(
            InventoryDTO("SKU_1", "A", 150),
            InventoryDTO("SKU_1", "B", 9),
        )
        every { mockedClient.listProducts() } returns listOf(PRODUCT_1)
        every { mockedClient.listInventories() } returns INVENTORIES

        val products = this.productService.listProducts()

        assertEquals(1, products.size)
        assertEquals("SKU_1", products[0].sku)
        assertEquals("kotlin", products[0].name)
        assertEquals(159, products[0].quantity)
    }
}