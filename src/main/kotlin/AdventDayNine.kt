package main.kotlin

import java.io.File
import java.security.NoSuchAlgorithmException


class AdventDayNine(private var path: String) {
    private var input: MutableMap<Int, Int>
    private var relativeBase = 0

    init {
        input = getInput()
    }

    private fun getInput(): MutableMap<Int, Int> {
        val values = mutableMapOf<Int, Int>()
        File(this.path).readLines().first().split(',').forEachIndexed { idx, it -> values[idx] = Integer.valueOf(it) }
        return values
    }

    fun run() {
        println("Day 5 - Part one: " + partOne(1))
        input = getInput()
    }

    private fun partOne(inputInt: Int): Int {
        var counter = 0
        var final = 0
        outer@ while (input.size > counter) {
            val code = input[counter]!!
            val opCode = code % 100
            if (opCode == 99) break@outer

            val params = mutableListOf<Int>()
            for (param in 1..2) {
                params.add(getParam(param, code, counter))
            }
            params.add(getAnwserParam(code, opCode, counter))

            when (opCode) {
                1, 2, 3, 7, 8 -> {
                    input[params[2]] = calcAnwser(opCode, params[0], params[1], inputInt)
                    counter += upCounter(opCode)
                }
                4 -> {
                    println("opCode $opCode, counter $counter, input $input")
                    final = params[0]
                    counter += upCounter(opCode)
                }
                5 -> if (params[0] != 0) counter = params[1] else counter += upCounter(opCode)
                6 -> if (params[0] == 0) counter = params[1] else counter += upCounter(opCode)
                9 -> {
                    calcAnwser(opCode, params[0], params[1], inputInt)
                    counter += upCounter(opCode)
                }
            }
        }
        return final
    }

    private fun upCounter(opCode: Int): Int {
        return when (opCode) {
            1, 2, 7, 8 -> 4
            3, 4, 9 -> 2
            5, 6 -> 3
            else -> throw NoSuchAlgorithmException()
        }
    }

    private fun calcAnwser(opCode: Int, param1: Int, param2: Int, inputInt: Int): Int {
        return when (opCode) {
            1 -> param1 + param2
            2 -> param1 * param2
            3 -> inputInt
            7 -> if (param1 < param2) 1 else 0
            8 -> if (param1 == param2) 1 else 0
            9 -> {
                relativeBase += param1
                relativeBase
            }
            else -> throw NoSuchAlgorithmException()
        }
    }

    private fun getAnwserParam(code: Int, opCode: Int, counter: Int): Int {
        return when (opCode) {
            1, 2, 7, 8 -> if(getMode(3, code) == 2) {
                input[counter+3+relativeBase]!!
            } else input[counter + 3]!!
            3 -> if(getMode(3, code) == 2) {
                input[counter + 1+relativeBase]!!
            } else input[counter + 1]!!
            else -> -999
        }
    }

    private fun getParam(param: Int, code: Int, counter: Int): Int {
        return when (getMode(param, code)) {
            0 -> getParamModeZero(counter, param)
            1 -> input[counter + param]!!
            2 -> if(param == 0) input[(input[counter]!! + relativeBase)]!! else 0
            else -> -999
        }
    }

    private fun getParamModeZero(counter: Int, param: Int): Int {
        return if (counter + param < input.size &&
            input[counter + param]!! < input.size
            && input[counter + param]!! >= 0
        ) {
            input[input[counter + param]]!!
        } else {
            0
        }
    }

    private fun getMode(param: Int, code: Int): Int {
        return if (param == 1) code % 1000 / 100 else code % 10000 / 1000
    }
}

