package main.kotlin

import java.io.File

class AdventDaySix(private var path: String) {
    private var input: List<String>
    private var counter = 0

    init {
        input = getInput()
    }

    private fun getInput(): List<String> {
        return File(this.path).readLines()
    }

    fun run() {
        println("Day 6 - Part one: " + partOne())
    }

    private fun partOne(): Int {
        val orbits = input.map { Pair(it.split(")")[0], it.split(")")[1]) }
        val allOrbits = mutableMapOf<String, MutableList<String>>()
        orbits.forEach {
            if (allOrbits.containsKey(it.second)) {
                allOrbits[it.second]!!.add(it.first)
                allOrbits[it.second] = allOrbits[it.second]!!
            } else {
                allOrbits[it.second] = mutableListOf(it.first)
            }
        }

        allOrbits.forEach {
            it.value.forEach { child ->
                getOrbits(allOrbits, child)
            }
        }
        return counter
    }

    private fun getOrbits(
        allOrbits: MutableMap<String, MutableList<String>>,
        subOrbit: String
    ) {
        if (allOrbits.containsKey(subOrbit)) {
            allOrbits[subOrbit]!!.forEach {
                counter++
                getOrbits(allOrbits, it)
            }
        } else {
            counter++
        }
    }
}
