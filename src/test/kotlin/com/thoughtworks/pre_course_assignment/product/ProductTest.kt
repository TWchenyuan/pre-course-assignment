package com.thoughtworks.pre_course_assignment.product

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class ProductTest {
    private val mockedClient = mockk<ProductClient>()
    private val productService = ProductService(mockedClient)

    @Test
    fun `should get empty product list from remote`() = runTest {
        coEvery { mockedClient.listProducts() } returns emptyList()

        val products = mockedClient.listProducts()

        assertEquals(0, products.size)
    }

    @Nested
    @Tag("Inventory")
    inner class InventoryTest {
        @Test
        fun `should get inventory when given a single region`() = runTest {
            coEvery { mockedClient.listProducts() } returns listOf(productBySku("kotlin", "SKU_1"))
            coEvery { mockedClient.listInventories() } returns listOf(InventoryDTO("SKU_1", "CHINA", 1000))

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals("SKU_1", products[0].sku)
            assertEquals("kotlin", products[0].name)
            assertEquals(1000, products[0].quantity)
        }

        @Test
        fun `should get 0 entities when given an empty inventory`() = runTest {
            coEvery { mockedClient.listProducts() } returns listOf(productBySku("kotlin", "SKU_1"))
            coEvery { mockedClient.listInventories() } returns emptyList()

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals("SKU_1", products[0].sku)
            assertEquals("kotlin", products[0].name)
            assertEquals(0, products[0].quantity)
        }

        @Test
        fun `should be 159 entities in total when 150 in region A and 9 in region B`() = runTest {
            coEvery { mockedClient.listProducts() } returns listOf(productBySku("watch", "SKU_1"))
            coEvery { mockedClient.listInventories() } returns listOf(
                InventoryDTO("SKU_1", "A", 150),
                InventoryDTO("SKU_1", "B", 9),
            )

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals("SKU_1", products[0].sku)
            assertEquals("watch", products[0].name)
            assertEquals(159, products[0].quantity)
        }

        @Test
        fun `should be counted when given multiple products`() = runTest {
            coEvery { mockedClient.listProducts() } returns listOf(
                productBySku("watch", "SKU_1"),
                productBySku("phone", "SKU_2")
            )
            coEvery { mockedClient.listInventories() } returns listOf(
                InventoryDTO("SKU_1", "A", 13),
                InventoryDTO("SKU_1", "B", 9),
                InventoryDTO("SKU_2", "B", 1),
                InventoryDTO("SKU_2", "C", 30),
            )

            val products = productService.listProducts()

            assertEquals(2, products.size)
            assertEquals(1, products.filter { it.sku == "SKU_1" }.size)
            assertEquals(1, products.filter { it.sku == "SKU_2" }.size)
            assertEquals(22, products.first { it.sku == "SKU_1" }.quantity)
            assertEquals(31, products.first { it.sku == "SKU_2" }.quantity)
        }
    }

    private fun productBySku(name: String, sku: String) =
        ProductDTO(name, sku, "NORMAL", BigDecimal(1), "image.jpg")

    @Nested
    @Tag("Price")
    inner class PriceTest {
        @Test
        fun `should get normal price when given normal product`() = runTest {
            coEvery { mockedClient.listProducts() } returns
                    listOf(normalProductByPrice("watch", "SKU_1", BigDecimal(1999)))
            coEvery { mockedClient.listInventories() } returns emptyList()

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals(BigDecimal(1999), products[0].price)

        }

        @Test
        fun `should get normal price when the quantity greater than 100`() = runTest {
            coEvery { mockedClient.listProducts() } returns
                    listOf(highDemandProductByPrice("watch", "SKU_1", BigDecimal(20)))
            coEvery { mockedClient.listInventories() } returns listOf(InventoryDTO("SKU_1", "A", 101))

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals(BigDecimal(20), products[0].price)
        }

        @Test
        fun `should get 120 percent of the price when the quantity between 30 and 100`() = runTest {
            coEvery { mockedClient.listProducts() } returns
                    listOf(highDemandProductByPrice("computer", "SKU_2", BigDecimal(20)))
            coEvery { mockedClient.listInventories() } returns listOf(
                InventoryDTO("SKU_2", "A", 20),
                InventoryDTO("SKU_2", "C", 30)
            )

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals("SKU_2", products[0].sku)
            assertEquals(BigDecimal.valueOf(24.0), products[0].price)
        }

        @Test
        fun `should get 150 percent of the price when the quantity less than 30`() = runTest {
            coEvery { mockedClient.listProducts() } returns
                    listOf(highDemandProductByPrice("computer", "SKU_2", BigDecimal(20)))
            coEvery { mockedClient.listInventories() } returns listOf(
                InventoryDTO("SKU_2", "A", 20),
            )

            val products = productService.listProducts()

            assertEquals(1, products.size)
            assertEquals("SKU_2", products[0].sku)
            assertEquals(BigDecimal.valueOf(30.0), products[0].price)
        }
    }

    private fun normalProductByPrice(name: String, sku: String, price: BigDecimal): ProductDTO =
        ProductDTO(name, sku, "NORMAL", price, "image.jpg")

    private fun highDemandProductByPrice(name: String, sku: String, price: BigDecimal): ProductDTO =
        ProductDTO(name, sku, "HIGH_DEMAND", price, "image.jpg")

}