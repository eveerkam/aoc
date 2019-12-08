package main.kotlin.helper

import java.security.NoSuchAlgorithmException

class IntcodeParser(val input: MutableList<Int>, var firstInputAmp: Int, var secInputAmp: Int) {

    var stopped: Boolean = false
    var counter = 0
    var firstInputIsUsed = false

    fun getOutputAmplifier(): Int {
        outer@ while (this.input.size > counter) {
            val code = input[counter]
            val opCode = code % 100

            if (opCode == 99) break@outer

            val params = mutableListOf<Int>()
            for (param in 1..2) {
                params.add(getParam(param, code, counter))
            }
            params.add(getAnwserParam(opCode, counter))

            when (opCode) {
                1, 2, 7, 8 -> {
                    input[params[2]] = calcAnwser(opCode, params[0], params[1])
                    counter += upCounter(opCode)
                }
                3 -> {
                    input[params[2]] = if (firstInputIsUsed) secInputAmp else firstInputAmp
                    firstInputIsUsed = true
                    counter += upCounter(opCode)
                }
                4 -> {
                    counter += upCounter(opCode)
                    return params[0]
                }
                5 -> if (params[0] != 0) counter = params[1] else counter += upCounter(opCode)
                6 -> if (params[0] == 0) counter = params[1] else counter += upCounter(opCode)
            }
        }
        stopped = true
        return 0
    }

    private fun upCounter(opCode: Int): Int {
        return when (opCode) {
            1, 2, 7, 8 -> 4
            3, 4 -> 2
            5, 6 -> 3
            else -> throw NoSuchAlgorithmException()
        }
    }

    private fun calcAnwser(opCode: Int, param1: Int, param2: Int): Int {
        return when (opCode) {
            1 -> param1 + param2
            2 -> param1 * param2
            7 -> if (param1 < param2) 1 else 0
            8 -> if (param1 == param2) 1 else 0
            else -> throw NoSuchAlgorithmException()
        }
    }

    private fun getAnwserParam(opCode: Int, counter: Int): Int {
        return when (opCode) {
            1, 2, 7, 8 -> input[counter + 3]
            3 -> input[counter + 1]
            else -> -999
        }
    }

    private fun getParam(param: Int, code: Int, counter: Int): Int {
        return if (getMode(param, code) == 0) {
            if (counter + param < input.size &&
                input[counter + param] < input.size
                && input[counter + param] >= 0
            ) {
                input[input[counter + param]]
            } else {
                0
            }
        } else {
            input[counter + param]
        }
    }

    private fun getMode(param: Int, code: Int): Any {
        return if (param == 1) code % 1000 / 100 else code % 10000 / 1000
    }


    fun changeSecondInput(secondInput: Int) {
        this.secInputAmp = secondInput
    }

}
