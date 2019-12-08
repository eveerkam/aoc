package main.kotlin

import main.kotlin.helper.IntcodeParser
import java.io.File


class AdventDaySeven(private var path: String) {
    private var input: MutableList<Int>

    init {
        input = getInput()
    }

    private fun getInput(): MutableList<Int> {
        return File(this.path).readLines().first().split(',').map { Integer.valueOf(it) }.toMutableList()
    }

    fun run() {
        println("Day 7 - Part one: " + partOne())
        input = getInput()
        println("Day 7 - Part two: " + partTwo())
    }

    private fun partTwo(): Int {
        val f = setOf(5, 6, 7, 8, 9)
        val g = setOf(5, 6, 7, 8, 9)
        val h = setOf(5, 6, 7, 8, 9)
        val i = setOf(5, 6, 7, 8, 9)
        val j = setOf(5, 6, 7, 8, 9)

        val fghij: Set<List<*>> = cartesianProduct(f, g, h, i, j)
        return fghij.filter { list -> list.groupingBy { it }.eachCount().filter { it.value > 1 }.isEmpty() }
            .map { inputArray ->
                getMax(
                    inputArray[0] as Int,
                    inputArray[1] as Int,
                    inputArray[2] as Int,
                    inputArray[3] as Int,
                    inputArray[4] as Int
                )
            }
            .max()!!
    }

    private fun getMax(a: Int, b: Int, c: Int, d: Int, e: Int): Int {
        var number: Int
        var max = 0
        val parser = IntcodeParser(input, a, 0)
        number = parser.getOutputAmplifier()
        val parser2 = IntcodeParser(input, b, number)
        number = parser2.getOutputAmplifier()
        val parser3 = IntcodeParser(input, c, number)
        number = parser3.getOutputAmplifier()
        val parser4 = IntcodeParser(input, d, number)
        number = parser4.getOutputAmplifier()
        val parser5 = IntcodeParser(input, e, number)
        number = parser5.getOutputAmplifier()
        parser.changeSecondInput(number)
        while (!parser5.stopped) {
            number = parser.getOutputAmplifier()
            parser2.changeSecondInput(number)
            number = parser2.getOutputAmplifier()
            parser3.changeSecondInput(number)
            number = parser3.getOutputAmplifier()
            parser4.changeSecondInput(number)
            number = parser4.getOutputAmplifier()
            parser5.changeSecondInput(number)
            number = parser5.getOutputAmplifier()
            parser.changeSecondInput(number)
            if (number > max) {
                max = number
            }
        }
        return max
    }


    private fun partOne(): Int {
        var final = 0
        val a = setOf(0, 1, 2, 3, 4)
        val b = setOf(0, 1, 2, 3, 4)
        val c = setOf(0, 1, 2, 3, 4)
        val d = setOf(0, 1, 2, 3, 4)
        val e = setOf(0, 1, 2, 3, 4)

        val abcde: Set<List<*>> = cartesianProduct(a, b, c, d, e)

        abcde.filter { list -> list.groupingBy { it }.eachCount().filter { it.value > 1 }.isEmpty() }
            .forEach { inputArray ->
                var lastOutput = 0
                inputArray.forEach { firstInput ->
                    firstInput as Int
                    val secondInput = lastOutput
                    lastOutput = IntcodeParser(input, firstInput, secondInput).getOutputAmplifier()
                }
                if (lastOutput >= final) {
                    final = lastOutput
                }
            }
        return final
    }

    fun cartesianProduct(vararg sets: Set<Int>): Set<List<*>> =
        when (sets.size) {
            0, 1 -> emptySet()
            else -> sets.fold(listOf(listOf<Any?>())) { acc, set ->
                acc.flatMap { list -> set.map { element -> list + element } }
            }.toSet()
        }
}

