package main.kotlin

import java.io.File

class AdventDaySix(private var path: String) {
    private var input: MutableList<Int>

    init {
        input = getInput()
    }

    private fun getInput(): MutableList<Int> {
        return File(this.path).readLines().first().split(',').map { Integer.valueOf(it) }.toMutableList()
    }

    fun run() {
        println("Day 6 - Part one: ")
    }
}
