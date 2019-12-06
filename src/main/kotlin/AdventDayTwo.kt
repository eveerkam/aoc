package main.kotlin

import java.io.File

class AdventDayTwo(path: String) {
    private var input: MutableList<Int>
    private var path: String
    private var index: Int = 0

    init {
        this.path = path
        input = getInput()
    }

    private fun getInput(): MutableList<Int> {
        return File(this.path).readLines().first().split(',').map { Integer.valueOf(it) }.toMutableList()
    }

    fun run() {
        println("Day 2 - Part one: " + partOne(12, 2))
        println("Day 2 - Part two: " + partTwo())
    }

    private fun partTwo(): Int {
        val output = 19690720
        for (noun in 0..99) {
            for (verb in 0..99) {
                reset()
                if (partOne(noun, verb) == output) {
                    return 100 * noun + verb
                }
            }
        }
        return -1
    }

    private fun reset() {
        index = 0
        input = getInput()
    }

    private fun partOne(noun: Int, verb: Int): Int {
        setStart(noun, verb)
        while (getCode() != 99) {
            when (getCode()) {
                1 -> opCodeAdd()
                2 -> opCodeMultiplie()
            }
            index += 4
        }
        return input[0]
    }

    private fun setStart(noun: Int, verb: Int) {
        input[1] = noun
        input[2] = verb
    }

    private fun getCode(): Int {
        return input[index]
    }

    private fun opCodeMultiplie() {
        input[input[index + 3]] = input[input[index + 1]] * input[input[index + 2]]
    }

    private fun opCodeAdd() {
        input[input[index + 3]] = input[input[index + 1]] + input[input[index + 2]]
    }
}
