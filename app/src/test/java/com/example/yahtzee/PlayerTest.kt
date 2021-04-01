package com.example.yahtzee

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.example.yahtzee.contracts.YahtzeeContract
import com.example.yahtzee.model.Dice
import com.example.yahtzee.model.DieResult
import com.example.yahtzee.model.Player
import com.example.yahtzee.model.Set
import org.junit.Test
import kotlin.concurrent.thread

class PlayerTest {


    //FULL HOUSE TESTS
    @Test
    fun `check full house with 1x3 and 1x2`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.ONE),
                Dice(result = DieResult.ONE), Dice(result = DieResult.ONE), Dice(result = DieResult.TWO),
                Dice(result = DieResult.TWO), Dice(result = DieResult.SIX)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.FULL_HOUSE_POINTS)
    }

    @Test
    fun `check full house with 2x3`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.ONE),
                Dice(result = DieResult.ONE), Dice(result = DieResult.ONE), Dice(result = DieResult.TWO),
                Dice(result = DieResult.TWO), Dice(result = DieResult.TWO)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.FULL_HOUSE_POINTS)
    }


    @Test
    fun `check full house with 1x2 and 1x4`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.ONE),
                Dice(result = DieResult.ONE), Dice(result = DieResult.TWO), Dice(result = DieResult.TWO),
                Dice(result = DieResult.TWO), Dice(result = DieResult.TWO)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.FULL_HOUSE_POINTS)
    }

    @Test
    fun `check full house with random order `() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.THREE),
                Dice(result = DieResult.THREE), Dice(result = DieResult.ONE), Dice(result = DieResult.THREE),
                Dice(result = DieResult.TWO), Dice(result = DieResult.ONE)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.FULL_HOUSE_POINTS)
    }


    @Test
    fun `check full house return zero with 3x2`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.ONE),
                Dice(result = DieResult.ONE), Dice(result = DieResult.TWO), Dice(result = DieResult.TWO),
                Dice(result = DieResult.THREE), Dice(result = DieResult.THREE)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)
    }


    @Test
    fun `check full house return zero with random different values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.TWO), Dice(result = DieResult.TWO), Dice(result = DieResult.THREE),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.SIX)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)
    }


    @Test
    fun `check full house return zero with same values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.FULL_HOUSE)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)
    }



    // getSetsFulfilment tests
    @Test
    fun `getSetsFulfilment test added value`() {
        val player = Player()
        player.rollDice()
        player.saveScore(Set.CHANCE)
        assertThat(player.getSetsFulfilment()).isEqualTo(1)
    }


    @Test
    fun `getSetsFulfilment test no values`() {
        val player = Player()
        assertThat(player.getSetsFulfilment()).isEqualTo(0)
    }


    //resetEndOfRound tests
    @Test
    fun `resetEndOfRound check dices after calling`() {
        val player = Player()
        player.rollDice()

        val oldDices = arrayListOf(player.diceToRoll)
        player.resetEndOfRound()
        assertThat(player.diceToRoll).isNotEqualTo(oldDices)
    }


    //getBonusValue test
    @Test
    fun `getBonusValue test no values`() {
        val player = Player()

        assertThat(player.getBonusValue()).isEqualTo(0)
    }

    @Test
    fun `getBonusValue test with values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.SIX), Dice(result = DieResult.FOUR)))
        player.saveScore(Set.FOURS)
        player.saveScore(Set.SIXES)
        player.saveScore(Set.THREE_OF_A_KIND)

        assertThat(player.getBonusValue()).isEqualTo(26)
    }


//    //getBonus result test
//    @Test
//    fun `getBonusResult test with values less then 63 returns 0`() {
//        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
//                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
//                Dice(result = DieResult.SIX), Dice(result = DieResult.FOUR)))
//        player.saveScore(Set.FOURS)
//        player.saveScore(Set.SIXES)
//
//        assertThat(player.getBonusResult()).isEqualTo(0)
//    }
//
//
//
//    @Test
//    fun `getBonusResult test with values more then 63 returns 35`() {
//        val player = Player()
//
//        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 15) as MutableMap<Set, Int>
//
//
//        assertThat(player.getBonusResult()).isEqualTo(YahtzeeContract.GameValues.BONUS_EXTRA_POINTS)
//    }
//
//    @Test
//    fun `getBonusResult test with values equal then 63 returns 35`() {
//        val player = Player()
//
//        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 12, Set.THREES to 1) as MutableMap<Set, Int>
//
//        assertThat(player.getBonusResult()).isEqualTo(YahtzeeContract.GameValues.BONUS_EXTRA_POINTS)
//    }

    //getTotalResult tests
    @Test
    fun `getTotalResult test with bonus scores`() {
        val player = Player()
        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 12, Set.THREES to 1) as MutableMap<Set, Int>


        assertThat(player.getTotalResult()).isEqualTo(98)
    }


    @Test
    fun `getTotalResult test without bonus scores`() {
        val player = Player()
        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 12, Set.THREES to 0) as MutableMap<Set, Int>


        assertThat(player.getTotalResult()).isEqualTo(62)
    }



    // saveScore tests
    @Test
    fun `saveScore test when set already exists`() {
        val player = Player()
        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 12, Set.THREES to 0) as MutableMap<Set, Int>


        assertThat(player.saveScore(Set.SIXES)).isEqualTo(false)
    }



    @Test
    fun `saveScore test when set not exists`() {
        val player = Player()
        player.setScores = mapOf(Set.SIXES to 50, Set.FOURS to 12, Set.THREES to 0) as MutableMap<Set, Int>


        assertThat(player.saveScore(Set.THREE_OF_A_KIND)).isEqualTo(true)
    }


    @Test
    // checkSmallStraight tests
    fun `checkSmallStraight with valid dice values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FIVE), Dice(result = DieResult.THREE), Dice(result = DieResult.SIX),
                Dice(result = DieResult.SIX), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.SMALL_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.SMALL_STRAIGHT_POINTS)

    }

    @Test
    fun `checkSmallStraight with valid dice values case two`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FIVE), Dice(result = DieResult.THREE), Dice(result = DieResult.SIX),
                Dice(result = DieResult.SIX), Dice(result = DieResult.TWO)))


        assertThat(player.chooseSet(Set.SMALL_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.SMALL_STRAIGHT_POINTS)

    }


    @Test
    fun `checkSmallStraight with non-valid dice values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.SIX), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.SMALL_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)

    }

    @Test
    fun `checkSmallStraight with non-valid dice values case two`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.THREE), Dice(result = DieResult.FIVE), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.THREE), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.SMALL_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)

    }


    //checkLargeStraight tests
    @Test
    fun `checkLargeStraight with valid dice values `() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.THREE), Dice(result = DieResult.FIVE), Dice(result = DieResult.SIX),
                Dice(result = DieResult.THREE), Dice(result = DieResult.TWO)))


        assertThat(player.chooseSet(Set.LARGE_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.LARGE_STRAIGHT_POINTS)

    }

    @Test
    fun `checkLargeStraight with non - valid dice values `() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.THREE), Dice(result = DieResult.FIVE), Dice(result = DieResult.SIX),
                Dice(result = DieResult.THREE), Dice(result = DieResult.FIVE)))


        assertThat(player.chooseSet(Set.LARGE_STRAIGHT)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)

    }


    //checkYahtzee
    @Test
    fun `checkYahtzee with valid dice values all same dice`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.YAHTZEE)).isEqualTo(YahtzeeContract.GameValues.YAHTZEE_POINTS)

    }

    @Test
    fun `checkYahtzee with valid dice values 5 same dice `() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))


        assertThat(player.chooseSet(Set.YAHTZEE)).isEqualTo(YahtzeeContract.GameValues.YAHTZEE_POINTS)

    }

    @Test
    fun `checkYahtzee with non-valid dice values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FIVE), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))

        assertThat(player.chooseSet(Set.YAHTZEE)).isEqualTo(YahtzeeContract.GameValues.ZERO_POINTS)

    }


    //Chance set tests
    @Test
    fun `check chance set with random values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FIVE), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))

        assertThat(player.chooseSet(Set.CHANCE)).isEqualTo(27)

    }


    @Test
    fun `check chance set with default values`() {
        val player = Player()

        assertThat(player.chooseSet(Set.CHANCE)).isEqualTo(-6)
    }


    // THREE_OF_A_KIND and FOUR_OF_A_KIND test
    @Test
    fun `checkForEqualsDice three same dice`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FIVE), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.TWO)))

        assertThat(player.chooseSet(Set.THREE_OF_A_KIND)).isEqualTo(25)
        assertThat(player.chooseSet(Set.FOUR_OF_A_KIND)).isEqualTo(0)
    }

    @Test
    fun `checkForEqualsDice two same dice`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FIVE), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.THREE), Dice(result = DieResult.TWO)))

        assertThat(player.chooseSet(Set.THREE_OF_A_KIND)).isEqualTo(0)
        assertThat(player.chooseSet(Set.FOUR_OF_A_KIND)).isEqualTo(0)
    }


    @Test
    fun `checkForEqualsDice four same dice`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.TWO)))

        assertThat(player.chooseSet(Set.THREE_OF_A_KIND)).isEqualTo(24)
        assertThat(player.chooseSet(Set.FOUR_OF_A_KIND)).isEqualTo(24)


    }
    @Test
    fun `checkForEqualsDice five same dice`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))

        assertThat(player.chooseSet(Set.THREE_OF_A_KIND)).isEqualTo(26)
        assertThat(player.chooseSet(Set.FOUR_OF_A_KIND)).isEqualTo(26)
    }



    //sumDiceNumberTest
    @Test
    fun `sumDiceNumber with random values`() {
        val player = Player(diceToRoll = arrayListOf(Dice(result = DieResult.SIX),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR),
                Dice(result = DieResult.FOUR), Dice(result = DieResult.FOUR)))

        assertThat(player.chooseSet(Set.FOURS)).isEqualTo(20)
        assertThat(player.chooseSet(Set.SIXES)).isEqualTo(6)
        assertThat(player.chooseSet(Set.TWOS)).isEqualTo(0)

    }





}