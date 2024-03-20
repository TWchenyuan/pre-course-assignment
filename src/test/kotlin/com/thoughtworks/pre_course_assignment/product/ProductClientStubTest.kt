package com.thoughtworks.pre_course_assignment.product

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProductClientStubTest {
    private lateinit var client: ProductClient
    private lateinit var mockedWebService: MockWebServer

    @BeforeEach
    fun setup() {
        mockedWebService = MockWebServer()
        mockedWebService.start()
        val retrofit = Retrofit.Builder().baseUrl(mockedWebService.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        client = retrofit.create(ProductClient::class.java)
    }

    @Test
    fun `should get products when fetch from remote`() = runTest {
        val response = MockResponse().setResponseCode(200).setBody(
            """
            [
              {
                "id": "1",
                "SKU": "ABC123",
                "name": "Electronic Watch",
                "price": 299.99,
                "type": "NORMAL",
                "image": "image1.jpg"
              },
              {
                "id": "2",
                "SKU": "DEF456",
                "name": "Sports Shoes",
                "price": 499.6,
                "type": "NORMAL",
                "image": "image2.jpg"
              }
            ]
        """.trimIndent()
        )
        mockedWebService.enqueue(response)

        val products = this@ProductClientStubTest.client.listProducts()
        assertNotNull(products)
        assertEquals(2, products.size)
        assertContentEquals(
            listOf(
                ProductDTO(
                    sku = "ABC123",
                    name = "Electronic Watch",
                    price = BigDecimal.valueOf(299.99),
                    type = "NORMAL",
                    image = "image1.jpg",
                ),
                ProductDTO(
                    sku = "DEF456",
                    name = "Sports Shoes",
                    price = BigDecimal.valueOf(499.6),
                    type = "NORMAL",
                    image = "image2.jpg",
                )
            ), products
        )
    }

    @AfterEach
    fun tearDown() {
        mockedWebService.shutdown()
    }
}