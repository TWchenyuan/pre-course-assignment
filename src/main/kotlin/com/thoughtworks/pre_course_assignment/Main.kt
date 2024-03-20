package com.thoughtworks.pre_course_assignment

import com.google.gson.Gson
import com.thoughtworks.pre_course_assignment.product.Product
import com.thoughtworks.pre_course_assignment.product.ProductClient
import com.thoughtworks.pre_course_assignment.product.ProductService
import com.thoughtworks.pre_course_assignment.product.toStringList
import com.thoughtworks.pre_course_assignment.util.PrintUtil
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun fetchProduct(): List<Product> = runBlocking {
    val retrofit = Retrofit.Builder().baseUrl("http://localhost:3000")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val service = ProductService(retrofit.create(ProductClient::class.java))
    val products = service.listProducts()
    products
}

fun main(args: Array<String>) {
    val printUtil = PrintUtil()
    val productStringList = fetchProduct().toStringList()
    printUtil.printTable(listOf("SKU", "name", "type","price", "quantity", "image"), productStringList)
}