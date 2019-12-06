package main.kotlin

import java.io.File

class AdventDayOne(path: String) {
    private var input: File = File(path)

    fun run() {
        partOne()
        partTwo()
    }

    private fun partTwo() {
        var fuelCounterUpper = 0
        input.forEachLine {
            var addFuel = calculateFuel(Integer.valueOf(it))
            while (addFuel > 0) {
                fuelCounterUpper += addFuel
                addFuel = calculateFuel(addFuel)
            }
        }
        println("Day 1 - Part one: " + fuelCounterUpper)
    }

    private fun partOne() {
        var fuelCounterUpper = 0
        input.forEachLine {
            fuelCounterUpper += calculateFuel(Integer.valueOf(it))
        }
        println("Day 1 - Part two: " + fuelCounterUpper)
    }

    private fun calculateFuel(weight: Int): Int {
        return (weight / 3) - 2
    }
}
