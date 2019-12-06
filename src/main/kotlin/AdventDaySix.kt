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
        // println("Day 6 - Part one: " + partOne())
        println("Day 6 - Part two: " + partTwo())
    }

    private fun partTwo(): Any? {
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

        var pathToMe =mutableListOf<String>()
        orbitsMap.filter { it.key == "YOU" }.forEach {
            it.value.forEach { child ->
                pathToMe = getOrbitsTwo(orbitsMap, child, pathToMe)
            }
        }
        pathToMe.forEachIndexed {idx, it -> println("$idx $it") }
        println("---")
       counter = 0

        var pathToSanta = mutableListOf<String>()
        orbitsMap.filter { it.key == "SAN" }.forEach {
            it.value.forEach { child ->
                pathToSanta = getOrbitsTwo(orbitsMap, child,pathToSanta)
            }
        }

        pathToSanta.forEachIndexed {idx, it -> println("$idx $it") }

        var lowestMeIndex = pathToMe.size
        pathToMe.forEachIndexed {meI, me ->
            pathToSanta.forEachIndexed{santaI, santa ->
                    if(me == santa && meI < lowestMeIndex){
                        println(santa)
                        println(me)
                        println(meI + santaI)
                        lowestMeIndex = meI
                    }
            }
         }
        return counter
    }

    private fun getOrbitsTwo(
        orbitsMap: MutableMap<String, MutableList<String>>,
        subOrbit: String,
        pathToMe: MutableList<String>
    ):MutableList<String>{
        if (orbitsMap.containsKey(subOrbit)) {
            orbitsMap[subOrbit]!!.forEach {
                counter++
                pathToMe.add(subOrbit)
                getOrbitsTwo(orbitsMap, it, pathToMe)
            }
        } else {
            pathToMe.add(subOrbit)
            counter++
        }
        return pathToMe
    }


    private fun partOne(): Int {
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

        orbitsMap.forEach {
            it.value.forEach { child ->
                getOrbits(orbitsMap, child)
            }
        }
        return counter
    }

    private fun getOrbits(
        orbitsMap: MutableMap<String, MutableList<String>>,
        subOrbit: String
    ) {
        if (orbitsMap.containsKey(subOrbit)) {
            orbitsMap[subOrbit]!!.forEach {
                counter++
                getOrbits(orbitsMap, it)
            }
        } else {
            counter++
        }
    }
}
