package main.kotlin

import java.io.File

class AdventDayThree(path: String) {
    private var input: File

    init {
        input = File(path)
    }

    fun run() {
        val inputOne = input.readLines()
            .first()
            .split(',')
        val inputTwo = input.readLines()
            .last()
            .split(',')

        val lineOne = getCoordinates(inputOne).distinct()
        val lineTwo = getCoordinates(inputTwo).distinct()

        println("Day 3 - Part one: " + partOne(lineOne, lineTwo))
        println("Day 3 - Part two: " + partTwo(lineOne, lineTwo))
    }

    private fun partTwo(lineOne: List<Coordinaat>, lineTwo: List<Coordinaat>): Int {
        return getMinStepsIntersection(lineOne, lineTwo)
    }


    private fun partOne(lineOne: List<Coordinaat>, lineTwo: List<Coordinaat>): Int {
        return lineOne.filter { lineTwo.contains(it) }
            .map { it.getSum() }
            .min()!!
    }

    private fun getMinStepsIntersection(lineOne: List<Coordinaat>, lineTwo: List<Coordinaat>): Int {
        var minSteps = 0
        lineOne.forEach {
            if (lineTwo.contains(it)) {
                val sumOfSteps = it.steps + lineTwo[lineTwo.indexOf(it)].steps
                if (sumOfSteps < minSteps || minSteps == 0) {
                    minSteps = sumOfSteps
                }
            }
        }
        return minSteps
    }


    private fun getCoordinates(input: List<String>): MutableList<Coordinaat> {
        val coordinates = mutableListOf(Coordinaat(0, 0, 0))
        var steps = 0
        input.forEach {
            val direction = it.substring(0, 1)
            val nrOfMovements = Integer.valueOf(it.substring(1))
            repeat(nrOfMovements) {
                steps += 1
                val last = coordinates.last()
                when (direction) {
                    "R" -> coordinates.add(Coordinaat(last.x, last.y + 1, steps))
                    "L" -> coordinates.add(Coordinaat(last.x, last.y - 1, steps))
                    "U" -> coordinates.add(Coordinaat(last.x + 1, last.y, steps))
                    "D" -> coordinates.add(Coordinaat(last.x - 1, last.y, steps))
                }
            }
        }
        coordinates.removeAt(0)
        return coordinates
    }
}

class Coordinaat(
    val x: Int,
    val y: Int,
    val steps: Int
) {
    fun getSum(): Int {
        val first = if (this.x < 0) this.x * -1 else this.x
        val last = if (this.y < 0) this.y * -1 else this.y
        return first + last
    }

    override fun equals(other: Any?): Boolean {
        other as Coordinaat
        return this.x == other.x && this.y == other.y
    }

    override fun toString(): String {
        return "x: " + this.x + " y: " + this.y + " steps: " + this.steps
    }

}



