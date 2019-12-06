package main.kotlin

class AdventDayFour(val min: Int, val max: Int) {


    fun run() {
        println("Day 4 - Part one: " + partOne(getAllIncreasingNumbers()))
        println("Day 4 - Part two: " + partTwo(getAllIncreasingNumbers()))
    }

    private fun partOne(numbers: MutableList<Int>): Int {
        val hitList = mutableListOf<Int>()
        numbers.forEach {
            if (it < this.max) {
                outer@ for (index in it.toString().toCharArray().indices) {
                    if (index < 5 && it.toString()[index] == it.toString()[index + 1]) {
                        hitList.add(it)
                        break@outer
                    }
                }
            }
        }
        return hitList.size
    }

    private fun partTwo(numbers: MutableList<Int>): Int {
        val hitList = mutableListOf<Int>()
        numbers.forEach {
            if (it < this.max) {
                outer@ for (index in it.toString().toCharArray().indices) {
                    if (index < 5 && it.toString()[index] == it.toString()[index + 1] &&
                        (index >= 4 || it.toString()[index] != it.toString()[index + 2]) &&
                        (index <= 0 || it.toString()[index] != it.toString()[index - 1])
                    ) {
                        hitList.add(it)
                        break@outer
                    }
                }
            }
        }
        return hitList.size
    }

    private fun getAllIncreasingNumbers(): MutableList<Int> {
        val hitList = mutableListOf<Int>()
        var counter = this.min

        while (counter <= this.max) {
            outerloop@ for (index in 0..5) {
                if (index < 5
                    && counter.toString()[index] > counter.toString()[index + 1]
                ) {
                    counter =
                        Integer.valueOf(
                            counter.toString().substring(0, index + 1) +
                                    counter.toString()[index] +
                                    getRest(index, counter)
                        )

                    break@outerloop
                }
            }
            hitList.add(counter)
            counter++
        }
        return hitList
    }

    fun getRest(index: Int, counter: Int): String {
        var rest = ""
        for (tel in index + 1 until 5) {
            rest += counter.toString()[index]
        }
        return rest
    }
}
