package main.kotlin

import java.io.File


class AdventDayEight(private var path: String) {
    private var input: String

    init {
        input = getInput()
    }

    private fun getInput(): String {
        return File(this.path).readLines().first()
    }

    fun run() {
        println("Day 8 - Part one: " + partOne())
        input = getInput()
        println("Day 8 - Part two: ")
        partTwo()
    }

    private fun partTwo() {
        val width = 25
        val length = 6
        val nrOfLayers = input.length / (width * length)
        val layerLength = input.length / nrOfLayers
        val lines = mutableListOf<MutableList<String>>()
        for (i in 0 until nrOfLayers) {
            val line = input.substring(i * layerLength, (i * layerLength) + layerLength)
            val layers = mutableListOf<String>()
            for (x in 0 until length) {
                val layer = line.substring(x * width, width + (x * width))

                layers.add(layer)
            }
            lines.add(layers)
        }

        val final = mutableListOf<Char>()

        for (x in 0 until length) {
            for (j in 0 until width) {
                val output = getFinal(lines, 0, x, j)
                final.add(output)
            }
        }

        for (i in 0 until width * length) {
            if (i != 0 && i % width == 0) {
                print("\n")
            }
            if (final[i] == '1') {
                print("\u2588")
            } else if (final[i] == '0') {
                print("\u2591")
            }
        }
    }


    private fun getFinal(
        lines: MutableList<MutableList<String>>,
        lineIndex: Int,
        layerIndex: Int,
        pixelIndex: Int
    ): Char {
        return when (lines[lineIndex][layerIndex][pixelIndex]) {
            '0', '1' -> lines[lineIndex][layerIndex][pixelIndex]
            else -> getFinal(lines, lineIndex + 1, layerIndex, pixelIndex)
        }
    }

    private fun partOne(): Int {
        val width = 25
        val length = 6
        val nrOfLayers = input.length / (width * length)
        val layerLength = input.length / nrOfLayers

        var sub = 0
        val layers = mutableListOf<String>()
        while (sub < input.length) {
            layers.add(input.substring(sub, sub + layerLength))
            sub += layerLength
        }

        var min = layerLength
        var index = -1
        layers.forEachIndexed { idx, layer ->
            val zeros = Regex("0").findAll(layer).count()
            if (zeros < min) {
                min = zeros
                index = idx
            }
        }
        return Regex("1").findAll(layers[index]).count() * Regex("2").findAll(layers[index]).count()
    }

}

