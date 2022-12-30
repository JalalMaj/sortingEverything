import java.util.Scanner

const val DATA_TYPE = "-dataType"
const val SORT_TYPE = "-sortingType"
fun main(args: Array<String>) {

    // Defines Objects required
    val strings = mutableListOf<String>()
    val numbers = mutableListOf<Int>()
    val scanner = Scanner(System.`in`)

    // Defines conditions to check
    val commandType = args.indexOf(DATA_TYPE) + 1 // java SortingTool -sortingType natural -dataType long
    val commandSort = args.indexOf(SORT_TYPE) + 1 // java SortingTool -sortingType natural -dataType long

    //Adding values to objects
    while (scanner.hasNext()) {
        when (args[commandType]) {
            "line" -> strings.add(scanner.nextLine()) // Adding complete line from console
            "long" -> numbers.add(scanner.nextInt()) // Adding integers from console
            else   -> strings.add(scanner.next())    // Adding words
        }
    }

    // Create a map with pairs (type, size) to be used for first item of output
    val (type, size) =
        when (args[commandType]) {
            "line" -> Pair("lines", strings.size)
            "long" -> Pair("numbers", numbers.size)
            else   -> Pair("words",strings.size)
        }
    // First Line of output of program
    println("Total $type: $size")

    // working on second Part of the output to find out byCount or natural for each dataType

    when (args[commandSort]) {
        // trying to create the format as stated in the problem
        "byCount" -> {
            val temporary = when (args[commandType]) {
                "long" -> numbers.sorted().groupingBy { it }.eachCount() //Counting repeated values
                else   -> strings.sorted().groupingBy { it }.eachCount()
            }

            //print out the sorted byCount depending on type long/word or line
            val sortedByCount = temporary.toList().sortedBy {
                (k,v) -> v
            }.toMap()

            for ((k,v) in sortedByCount) {
                println("$k: $v time(s), ${v * 100 / size}%")
            }
        }
        else -> {
            when (args[commandType]) {
                "line" -> {
                    println("Sorted data:")
                    strings.forEach(::println)
                }
                "long" -> {
                    println("Sorted data: ${numbers.sorted().joinToString(" ")}")
                }
                else -> println("Sorted data: ${strings.sorted().joinToString(" ")}")
            }
        }
    }
}