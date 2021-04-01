package com.example.yahtzee.model

import android.util.Log
import com.example.yahtzee.contracts.YahtzeeContract
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "Player"

class Player(
    var id: UUID = UUID.randomUUID(),
    var name: String = "Player",
    var diceToRoll: ArrayList<Dice> =  arrayListOf(Dice(), Dice(), Dice(), Dice(), Dice(), Dice()),
    var setScores: MutableMap<Set, Int> = mutableMapOf(),
    var playerTurn: Boolean = false,
    var rollCount: Int = YahtzeeContract.GameValues.ROLLING_ALLOWED
) {

    override fun toString(): String { // used for logs
        return "${this.id}, ${this.name}, ${this.diceToRoll}, ${this.setScores}, ${this.playerTurn}, ${this.rollCount}"
    }

    fun getSetsFulfilment() : Int { // if it is 13 player filled all sets
        Log.d(TAG, "getSetsFulfilment: starts")
        return setScores.keys.count()
    }

    fun resetEndOfRound() { // reset when player turn ends
        Log.d(TAG, "resetEndOfRound: starts")
        rollCount = YahtzeeContract.GameValues.ROLLING_ALLOWED
        getNewDice()
        playerTurn = false
    }

    fun saveDice(dice: Dice) {
        Log.d(TAG, "saveDice: starts with $dice")
        dice.savedDie = dice.savedDie != true // true to false, false to true
        Log.d(TAG, "saveDice: ends with $dice")
    }

    private fun getNewDice() { // create new list of dice instead of changing values of current dice (result, savedDie)
        Log.d(TAG, "getNewDices: starts with $diceToRoll")
        val diceList = ArrayList<Dice>()
        for(i in 1..6) {
            diceList.add(Dice())
        }
        diceToRoll = diceList
        Log.d(TAG, "getNewDices: ends with $diceToRoll")
    }

    fun getTotalResult() : Int {
        Log.d(TAG, "getTotalResult: starts")
        return setScores.map { it.value }.sum() + getBonusResult()
    }

    fun rollDice() {
        Log.d(TAG, "rollDice: starts with $diceToRoll")
        diceToRoll.filter { !it.savedDie }.forEach { die -> // throw only non saved dice
            die.randomizeResult()

            Log.d(TAG, "rollDice: rolled dice is $die")
        }
        rollCount--
        Log.d(TAG, "rollDice: ends with $diceToRoll")
    }

    fun saveScore(scoreSet: Set): Boolean { // returns false if set is already filled
        Log.d(TAG, "saveScore: starts with $scoreSet")
        val result = chooseSet(scoreSet)
        return if(setScores[scoreSet] == null) {
            setScores[scoreSet] = result
            Log.d(TAG, "saveScore: ends with $scoreSet and $result, true")
            true
        } else {
            Log.d(TAG, "saveScore: ends with $scoreSet and $result, false")
            false
        }
    }

    fun getBonusValue() : Int { // bonus is calculated with primary set
        Log.d(TAG, "getBonusValue: starts")
        val result = setScores.filter { it.key == Set.ACES || it.key == Set.TWOS
                ||  it.key == Set.THREES ||  it.key == Set.FOURS
                || it.key == Set.FIVES || it.key == Set.SIXES}.map { it.value }.sum()
        Log.d(TAG, "getBonusValue: ends with $result")
        return result
    }

    private fun getBonusResult() : Int {
        Log.d(TAG, "getBonusResult: starts")
        val result = if(getBonusValue() >= YahtzeeContract.GameValues.BONUS_IS_ACHIEVED) {
            YahtzeeContract.GameValues.BONUS_EXTRA_POINTS
        } else
            YahtzeeContract.GameValues.ZERO_POINTS
        Log.d(TAG, "getBonusResult: ends with $result")
        return result
    }


    fun chooseSet(set: Set): Int {
        Log.d(TAG, "chooseSet: starts with $set")
        val result = when(set) {
            Set.ACES -> { // sum all die with result one
                sumDiceNumber(DieResult.ONE.value)
            }
            Set.TWOS -> {
                sumDiceNumber(DieResult.TWO.value)
            }
            Set.THREES -> {
                sumDiceNumber(DieResult.THREE.value)
            }
            Set.FOURS -> {
                sumDiceNumber(DieResult.FOUR.value)
            }
            Set.FIVES -> {
                sumDiceNumber(DieResult.FIVE.value)
            }
            Set.SIXES -> {
                sumDiceNumber(DieResult.SIX.value)
            }
            Set.THREE_OF_A_KIND -> { // if there is three die with same result returns sum of all dies otherwise 0
                if(checkForEqualsDice(Set.THREE_OF_A_KIND) || checkForEqualsDice(Set.FOUR_OF_A_KIND)) {
                    diceToRoll.sumBy { it.result.value }
                }
                else YahtzeeContract.GameValues.ZERO_POINTS
            }
            Set.FOUR_OF_A_KIND -> {
                if(checkForEqualsDice(Set.FOUR_OF_A_KIND)) {
                    diceToRoll.sumBy { it.result.value }
                }
                else YahtzeeContract.GameValues.ZERO_POINTS
            }
            Set.FULL_HOUSE -> { // 3 same die and one pair die
                if(checkFullHouse()) YahtzeeContract.GameValues.FULL_HOUSE_POINTS else YahtzeeContract.GameValues.ZERO_POINTS
            }
            Set.SMALL_STRAIGHT -> { // at least four in a row
                if(checkSmallStraight()) YahtzeeContract.GameValues.SMALL_STRAIGHT_POINTS else YahtzeeContract.GameValues.ZERO_POINTS
            }
            Set.LARGE_STRAIGHT -> { // at least five in a row
                if(checkLargeStraight()) YahtzeeContract.GameValues.LARGE_STRAIGHT_POINTS else YahtzeeContract.GameValues.ZERO_POINTS
            }

            Set.YAHTZEE-> { // five same dice
                if(checkYahtzee()) YahtzeeContract.GameValues.YAHTZEE_POINTS else YahtzeeContract.GameValues.ZERO_POINTS
            }
            Set.CHANCE-> { // sum all dice
                diceToRoll.sumBy { it.result.value }
            }
        }
        Log.d(TAG, "chooseSet: ends with $set and result $result")
        return result
    }

    private fun sumDiceNumber(number: Int) : Int { // it will sum all ones or all twos etc.
        Log.d(TAG, "sumDiceNumber: starts with $number")
        val result = diceToRoll.map { it.result.value }.filter { it == number }.sum()
        Log.d(TAG, "sumDiceNumber: ends with $result")
        return result
    }


    private fun checkForEqualsDice(setType: Set) : Boolean { // check if there is three or four same dice
        Log.d(TAG, "checkFourEqualsDice: starts with $setType")
        val countedResults = diceToRoll.map { it.result.value }.groupingBy { it }.eachCount() // fore result 1,1,2,3,3,3 -> 1=1, 2=1, 3=3
        val result = when(setType) {
            Set.THREE_OF_A_KIND -> {
                countedResults.values.any { it >= 3 }
            }
            Set.FOUR_OF_A_KIND -> {
                countedResults.values.any { it >= 4 }
            }
            else -> false
        }

        Log.d(TAG, "checkFourEqualsDice: ends with $setType and result $result")
        return result
    }

    private fun checkFullHouse() : Boolean {
        Log.d(TAG, "checkFullHouse: starts")
        val result = if(checkForEqualsDice(Set.FOUR_OF_A_KIND) || checkForEqualsDice(Set.THREE_OF_A_KIND)) { // if there isn't 3 or 4 same die no need for more checking
            val countedResults = diceToRoll.map { it.result.value }.groupingBy { it }.eachCount()

            val ejectValue = countedResults.values.find { it >= 3 } // this will return first element of map that has value >=3

            val remainList = countedResults.values.minusElement(ejectValue) // remove first element that corresponds

            val finalResult = remainList.any { it!! >= 2 } // check if pair exists

            finalResult
        } else false

        Log.d(TAG, "checkFullHouse: ends with $result")
        return result
    }

    private fun checkSmallStraight() : Boolean { // 4 in a row
        Log.d(TAG, "checkSmallStraight: starts")
        val diceResults = diceToRoll.map { it.result.value }
        val firstCase = mutableListOf(1,2,3,4)
        val secondCase = mutableListOf(2,3,4,5)
        val thirdCase = mutableListOf(3,4,5,6)
        val result = diceResults.containsAll(firstCase) || diceResults.containsAll(secondCase) || diceResults.containsAll(thirdCase)
        Log.d(TAG, "checkSmallStraight: ends with $result")
        return result
    }

    private fun checkLargeStraight() : Boolean { // 5 in a row
        Log.d(TAG, "checkLargeStraight: starts")
        val diceResults = diceToRoll.map { it.result.value }
        val firstCase = mutableListOf(1,2,3,4,5)
        val secondCase = mutableListOf(2,3,4,5,6)

        val result = diceResults.containsAll(firstCase) || diceResults.containsAll(secondCase)
        Log.d(TAG, "checkLargeStraight: ends with $result")
        return result
    }


    private fun checkYahtzee() : Boolean {
        Log.d(TAG, "checkYahtzee: starts")

        val countedResults = diceToRoll.map { it.result.value }.groupingBy { it }.eachCount()// for result 1,1,2,3,3,3 -> 1=1, 2=1, 3=3
        val result = countedResults.any { it.value >= 5}

        Log.d(TAG, "checkYahtzee: ends with $result")
        return result
    }

}