package sorting

import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    // creating Sorter object and checking args
    val sorter = try {
        setDataAndSortingType(args)
    } catch (e: Exception) {
        println(e.message)
    }
}
fun setDataAndSortingType(args: Array<String>): Sorter {
    val allowedDataTypes = listOf("long", "word", "line")
    val allowedSortingTypes = listOf("natural", "byCount")

    var dataType = "word"
    var sortingType = "natural"
    for (arg in args) {
        if (arg.matches("-.+".toRegex())) {
            when (arg) {
                "-dataType" -> { dataType = args.find { it in allowedDataTypes }
                    ?: throw Exception("No data type defined!")
                }
                "-sortingType" -> { sortingType = args.find { it in allowedSortingTypes }
                    ?: throw Exception("No sorting type defined!")
                }
                else -> println("\"$arg\" is not a valid parameter. It will be skipped.")
            }
        }
    }
    return Sorter(sortingType, dataType)
}
class Sorter(val sortingType: String, val dataType: String) {
    val inputStrings = mutableListOf<String>()
    val inputDigits = mutableListOf<Int>()
    init {
        val scanner = Scanner(System.`in`)
        while (scanner.hasNext()) {
            when (dataType) {
                "long" -> {
                    val next = scanner.next()
                    try {
                        inputDigits.add(next.toInt())
                    } catch (e: Exception) {
                        println("\"$next\" is not a long. It will be skipped.")
                    }
                }
                "line" -> inputStrings.add(scanner.nextLine())
                else -> inputStrings.add(scanner.next())
            }
        }
        when (dataType) {
            "long" -> println("Total numbers: ${inputDigits.size}.")
            else -> println("Total ${dataType}s: ${inputStrings.size}.")
        }
        when (sortingType) {
            "natural" -> naturalSorting()
            else -> sortByCount()
        }
    }
    fun naturalSorting() {
        when (dataType) {
            "line" -> {
                println("Sorted data:")
                inputStrings.sorted().onEach { println(it) }
            }
            "long" -> println("Sorted data: ${inputDigits.sortedBy { it }.joinToString(" ")}")
            else -> println("Sorted data: ${inputStrings.sorted().joinToString(" ")}")
        }
    }
    fun sortByCount() {
        val sortedList = when (dataType) {
            "long" -> inputDigits.sorted()
            else -> inputStrings.sorted()
        }
        val eachPart: Double = 100.00 / sortedList.size
        val countMap = sortedList.groupingBy { it }.eachCount()
        countMap.toList().sortedBy { (key, value) -> value }.toMap().forEach {
            println("${it.key}: ${it.value} time(s), ${(eachPart * it.value).roundToInt()}%")
        }
    }
}