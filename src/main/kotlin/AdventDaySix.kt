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
        println("Day 6 - Part two: " + partTwo())
    }

    private fun partOne(): Int {
        val orbitsMap = getOrbitsMap()
        orbitsMap.forEach {
            it.value.forEach { child ->
                getOrbits(orbitsMap, child, mutableListOf())
            }
        }
        return counter
    }

    private fun partTwo(): Any? {
        val pathToMe = getMapToOrbit("YOU")
        val pathToSanta = getMapToOrbit( "SAN")

        val firstMatchingOrbit = pathToMe.filter { me -> pathToSanta.any { it == me } }.first()
        val indexMeFirstMatch = pathToMe.indexOf(firstMatchingOrbit)
        val indexSantaFirstMatch = pathToSanta.indexOf(firstMatchingOrbit)

        return indexMeFirstMatch + indexSantaFirstMatch
    }

    private fun getOrbits(
        orbitsMap: MutableMap<String, MutableList<String>>,
        subOrbit: String,
        pathToMe: MutableList<String> // partTwo
    ): MutableList<String> {
        if (orbitsMap.containsKey(subOrbit)) {
            orbitsMap[subOrbit]!!.forEach {
                counter++
                pathToMe.add(subOrbit) // partTwo
                getOrbits(orbitsMap, it, pathToMe)
            }
        } else {
            pathToMe.add(subOrbit) // partTwo
            counter++
        }
        return pathToMe // partTwo
    }

    private fun getOrbitsMap(): MutableMap<String, MutableList<String>> {
        val orbits = input.map { Pair(it.split(")")[0], it.split(")")[1]) }
        val orbitsMap = mutableMapOf<String, MutableList<String>>()

        orbits.forEach {
            if (orbitsMap.containsKey(it.second)) {
                orbitsMap[it.second]!!.add(it.first)
                orbitsMap[it.second] = orbitsMap[it.second]!!
            } else {
                orbitsMap[it.second] = mutableListOf(it.first)
            }
        }
        return orbitsMap
    }

    fun getMapToOrbit(key: String): MutableList<String> {
        var pathToOrbit = mutableListOf<String>()
        getOrbitsMap()[key]!!.forEach { orbit ->
            pathToOrbit = getOrbits( getOrbitsMap(),orbit, pathToOrbit)
        }
        return pathToOrbit
    }

}
