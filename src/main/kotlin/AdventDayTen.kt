package main.kotlin

import jdk.nashorn.internal.runtime.JSType.toDouble
import jdk.nashorn.internal.runtime.JSType.toInt32
import java.io.File
import java.lang.Math.toDegrees
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.streams.toList


class AdventDayTen(private var path: String) {
    private var input: MutableMap<Int, String>

    init {
        input = getInput()
    }

    private fun getInput(): MutableMap<Int, String> {
        val values = mutableMapOf<Int, String>()
        File(this.path).readLines().forEachIndexed { idx, it -> values.put(idx, it) }
        return values
    }

    fun run() {
        println("Day 10 - Part one: " + partOne())
        println("Day 10 - Part two: " + partTwo())
    }

    private fun partOne(): Int {
        val points = getPoints()
        return points.filter { it.type == "#" }.map { getAstroidsInView(it) }.max()!!
    }

    private fun partTwo(): Int {
        val points = getPoints()
        var maxAstroidsInView = 0
        var laserAstroid: Point? = null

        points.filter { it.type == "#" }.forEach {
            val astroidsInView = getAstroidsInView(it)
            if (astroidsInView >= maxAstroidsInView) {
                maxAstroidsInView = astroidsInView
                laserAstroid = it
            }
        }

        val angleWithPoints = points
            .filter { it.type == "#" }
            .filter { it != laserAstroid }
            .sortedBy { it.distance(laserAstroid!!) }
            .groupByTo(mutableMapOf()) { it.getAngle(laserAstroid!!) }

        val angles = angleWithPoints.keys.stream().sorted().toList()

        var anglesStart = angles.indexOf(270.0)

        val removed = mutableListOf<Point>()
        while (removed.size != 200) {
            if (angleWithPoints[angles[anglesStart % angleWithPoints.size]]!!.isNotEmpty()) {
               println(""+ (removed.size+1) + " "+ angles[anglesStart % angleWithPoints.size] + " " +angleWithPoints[angles[anglesStart % angleWithPoints.size]]!![0])
                removed.add(angleWithPoints[angles[anglesStart % angleWithPoints.size]]!![0])
                angleWithPoints[angles[anglesStart % angleWithPoints.size]]!!.removeAt(0)
                anglesStart++
            }
        }
        return ((removed.last().x * 100) + removed.last().y).toInt()
    }

    private fun getPoints(): MutableList<Point> {
        val points = mutableListOf<Point>()
        input.forEach { y ->
            y.value.forEachIndexed { idx, x -> points.add(Point(toDouble(idx), toDouble(y.key), x.toString())) }
        }
        return points
    }

    private fun getAstroidsInView(astroid: Point): Int {
        return getAngleToAllAstroids(astroid).values
            .distinct()
            .count()
    }

    private fun getAngleToAllAstroids(astroid: Point): MutableMap<Point, Double> {
        val astroidWithAngle = mutableMapOf<Point, Double>()
        getPoints()
            .asSequence()
            .filter { it.type == "#" }
            .filter { it != astroid }
            .forEach {
                astroidWithAngle[it] = astroid.getAngle(it)!!
            }
        return astroidWithAngle
    }

}


class Point( val x: Double, val y: Double,val type: String) {

    fun getAngle(other: Point): Double? {
        var angle = toDegrees(atan2(other.y - y, other.x - x)) + 180
        if (angle < 0) angle += 360.0
        return angle
    }

    override fun equals(other: Any?): Boolean {
        other as Point
        return this.x == other.x && this.y == other.y
    }

    override fun toString(): String {
        return "X=$x Y=$y"
    }

    fun distance(laserAstroid: Point): Double {
        return abs(x - laserAstroid.x) + abs(y - laserAstroid.y)
    }
}
