package com.thoughtworks.pre_course_assginment.util

class PrintUtil {
    fun printTable(headers: List<String>, rows: List<List<String>>) {
        // 计算每列的最大宽度
        val columnWidths = MutableList(headers.size) { 0 }
        for (i in headers.indices) {
            columnWidths[i] = headers[i].length
        }
        for (row in rows) {
            for (i in row.indices) {
                if (row[i].length > columnWidths[i]) {
                    columnWidths[i] = row[i].length
                }
            }
        }

        // 创建分隔线
        val separator = columnWidths.joinToString("+", "+", "+") { "-".repeat(it + 2) }

        // 打印表头
        println(separator)
        print("|")
        for ((index, header) in headers.withIndex()) {
            print(" ${header.padEnd(columnWidths[index])} |")
        }
        println()
        println(separator)

        // 打印数据行
        for (row in rows) {
            print("|")
            for ((index, cell) in row.withIndex()) {
                print(" ${cell.padEnd(columnWidths[index])} |")
            }
            println()
        }

        // 打印底部分隔线
        println(separator)
    }
}
