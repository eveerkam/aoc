package main.kotlin

import java.io.File
import kotlin.math.abs

class AdventDayTwelve(private var path: String) {
    private var moons: List<Moon>
    private val moonNames = mutableListOf("Io", "Europa", "Ganymede", "Callisto")

    init {
        moons = getInput()
    }

    private fun getInput(): List<Moon> {
        val moons = mutableListOf<Moon>()
        File(this.path).readLines().forEachIndexed { idx, rawCoordinates ->
            val coordinates = mutableMapOf<String, Int>()
            rawCoordinates.removeSurrounding("<", ">")
                .split(",")
                .forEach {
                    val coordinate = it.trim().split("=")
                    coordinates[coordinate.first()] = Integer.valueOf(coordinate.last().toString())
                }
            moons.add(
                Moon(
                    moonNames[idx],
                    coordinates["x"]!!,
                    coordinates["y"]!!,
                    coordinates["z"]!!,
                    Moon(moonNames[idx], 0, 0, 0, null)
                )
            )
        }
        return moons
    }

    fun run() {
        println("Day 12 - Part one: " + partOne())
    }

    private fun partOne(): Int {
        for (x in 1 until 1001 step 1) {
            println("After $x steps:")
            val gravities = moons.map { moon -> moon.getGravity(moons.filter { moon.name != it.name }) }
            moons.forEach {
                it.step(gravities.first { gravity -> gravity.name == it.name })
                println(it)
            }
            println()
        }

        return moons.map { it.getTotalEnergie() }.sum()
    }
}

class Moon(val name: String, var x: Int, var y: Int, var z: Int, var velocity: Moon?) {
    fun getGravity(other: List<Moon>): Moon {
        val gravityX = getGravity("x", other)
        val gravityY = getGravity("y", other)
        val gravityZ = getGravity("z", other)
        return Moon(name, gravityX, gravityY, gravityZ, velocity)
    }

    private fun getGravity(dimension: String, other: List<Moon>): Int {
        return when (dimension) {
            "x" -> other.map { it.x }.map { if (it > x) 1 else if (it < x) -1 else 0 }.sum()
            "y" -> other.map { it.y }.map { if (it > y) 1 else if (it < y) -1 else 0 }.sum()
            "z" -> other.map { it.z }.map { if (it > z) 1 else if (it < z) -1 else 0 }.sum()
            else -> throw NoSuchFieldException()
        }
    }

    fun step(gravity: Moon) {
        x += gravity.x + gravity.velocity!!.x
        y += gravity.y + gravity.velocity!!.y
        z += gravity.z + gravity.velocity!!.z
        velocity!!.x = gravity.x + gravity.velocity!!.x
        velocity!!.y = gravity.y + gravity.velocity!!.y
        velocity!!.z = gravity.z + gravity.velocity!!.z
    }

    override fun toString(): String {
        val velocityString = ", vel<x=" + velocity!!.x + ", y=" + velocity!!.y + ", z=" + velocity!!.z +">"
        return "<x=$x, y=$y, z=$z> $velocityString"
    }

    fun getTotalEnergie(): Int {
        return this.getPot() * getKen()
    }

    private fun getKen(): Int {
        return abs(x) + abs(y) + abs(z)
    }

    private fun getPot(): Int {
        return abs(velocity!!.x) + abs(velocity!!.y) + abs(velocity!!.z)
    }
}
