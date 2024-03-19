package com.thoughtworks.pre_course_assignment

import com.thoughtworks.pre_course_assignment.util.PrintUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class PrintUtilTest {
    private val util = PrintUtil()
    private val outputStreamCaptor = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach
    fun setup() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun teardown() {
        System.setOut(originalOut)
    }

    @Test
    fun `should print table when give a product`() {
        val headers = listOf("SKU", "name", "price", "quantity", "image")
        val product = listOf("SKU_1", "Watch", "2999.0", "1000", "image.jpg")
        util.printTable(headers, listOf(product))
        assertEquals(
            """
+-------+-------+--------+----------+-----------+
| SKU   | name  | price  | quantity | image     |
+-------+-------+--------+----------+-----------+
| SKU_1 | Watch | 2999.0 | 1000     | image.jpg |
+-------+-------+--------+----------+-----------+
        """.trimIndent(), outputStreamCaptor.toString().trim()
        )
    }
}