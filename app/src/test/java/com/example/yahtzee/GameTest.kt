package com.example.yahtzee

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.yahtzee.model.*
import com.example.yahtzee.model.Set
import org.junit.Test

class GameTest {

    //nextPlayer tests
    @Test
    fun `nextPlayer first player was on turn`() {
        val player1 = Player(playerTurn = true)

        val player2 = Player(playerTurn = false)

        val game = Game(players = arrayListOf(player1, player2))

        game.nextPlayer()
        assertThat(player1.playerTurn).isEqualTo(false)
        assertThat(player2.playerTurn).isEqualTo(true)
    }

    @Test
    fun `nextPlayer second player was on turn`() {
        val player1 = Player(playerTurn = false)

        val player2 = Player(playerTurn = true)

        val game = Game(players = arrayListOf(player1, player2))

        game.nextPlayer()
        assertThat(player1.playerTurn).isEqualTo(true)
        assertThat(player2.playerTurn).isEqualTo(false)
    }


    @Test
    fun `nextPlayer none of players was on turn first player should be true`() {
        val player1 = Player(playerTurn = false)

        val player2 = Player(playerTurn = false)

        val game = Game(players = arrayListOf(player1, player2))

        game.nextPlayer()
        assertThat(player1.playerTurn).isEqualTo(true)
        assertThat(player2.playerTurn).isEqualTo(false)
    }



    //checkGameOver tests
    @Test
    fun `checkGameOver none of player have filled sets returns false`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.LARGE_STRAIGHT)
        player2.saveScore(Set.LARGE_STRAIGHT)
        player2.saveScore(Set.TWOS)
        val game = Game(players = arrayListOf(player1, player2))

        assertThat(game.checkGameOver()).isEqualTo(false)
    }


    @Test
    fun `checkGameOver one of player have filled sets returns false`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.ACES)
        player1.saveScore(Set.TWOS)
        player1.saveScore(Set.THREES)
        player1.saveScore(Set.FOURS)
        player1.saveScore(Set.FIVES)
        player1.saveScore(Set.SIXES)
        player1.saveScore(Set.THREE_OF_A_KIND)
        player1.saveScore(Set.FOUR_OF_A_KIND)
        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.CHANCE)
        player1.saveScore(Set.FULL_HOUSE)
        player1.saveScore(Set.SMALL_STRAIGHT)
        player1.saveScore(Set.LARGE_STRAIGHT)

        player2.saveScore(Set.ACES)
        val game = Game(players = arrayListOf(player1, player2))

        assertThat(player1.getSetsFulfilment()).isEqualTo(13)
        assertThat(game.checkGameOver()).isEqualTo(false)
    }

    @Test
    fun `checkGameOver both of player have filled sets returns true`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.ACES)
        player1.saveScore(Set.TWOS)
        player1.saveScore(Set.THREES)
        player1.saveScore(Set.FOURS)
        player1.saveScore(Set.FIVES)
        player1.saveScore(Set.SIXES)
        player1.saveScore(Set.THREE_OF_A_KIND)
        player1.saveScore(Set.FOUR_OF_A_KIND)
        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.CHANCE)
        player1.saveScore(Set.FULL_HOUSE)
        player1.saveScore(Set.SMALL_STRAIGHT)
        player1.saveScore(Set.LARGE_STRAIGHT)

        player2.saveScore(Set.ACES)
        player2.saveScore(Set.TWOS)
        player2.saveScore(Set.THREES)
        player2.saveScore(Set.FOURS)
        player2.saveScore(Set.FIVES)
        player2.saveScore(Set.SIXES)
        player2.saveScore(Set.THREE_OF_A_KIND)
        player2.saveScore(Set.FOUR_OF_A_KIND)
        player2.saveScore(Set.YAHTZEE)
        player2.saveScore(Set.CHANCE)
        player2.saveScore(Set.FULL_HOUSE)
        player2.saveScore(Set.SMALL_STRAIGHT)
        player2.saveScore(Set.LARGE_STRAIGHT)

        val game = Game(players = arrayListOf(player1, player2))

        assertThat(player1.getSetsFulfilment()).isEqualTo(13)
        assertThat(player2.getSetsFulfilment()).isEqualTo(13)
        assertThat(game.checkGameOver()).isEqualTo(true)
    }





    //checkWinner tests
    @Test
    fun `checkWinner players different results first player wins`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.ACES)
        player1.saveScore(Set.TWOS)
        player1.saveScore(Set.THREES)
        player1.saveScore(Set.FOURS)
        player1.saveScore(Set.FIVES)
        player1.saveScore(Set.SIXES)
        player1.saveScore(Set.THREE_OF_A_KIND)
        player1.saveScore(Set.FOUR_OF_A_KIND)
        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.CHANCE)
        player1.saveScore(Set.FULL_HOUSE)
        player1.saveScore(Set.SMALL_STRAIGHT)
        player1.saveScore(Set.LARGE_STRAIGHT)

        player1.setScores[Set.SIXES] = 10

        player2.saveScore(Set.ACES)
        player2.saveScore(Set.TWOS)
        player2.saveScore(Set.THREES)
        player2.saveScore(Set.FOURS)
        player2.saveScore(Set.FIVES)
        player2.saveScore(Set.SIXES)
        player2.saveScore(Set.THREE_OF_A_KIND)
        player2.saveScore(Set.FOUR_OF_A_KIND)
        player2.saveScore(Set.YAHTZEE)
        player2.saveScore(Set.CHANCE)
        player2.saveScore(Set.FULL_HOUSE)
        player2.saveScore(Set.SMALL_STRAIGHT)
        player2.saveScore(Set.LARGE_STRAIGHT)

        val game = Game(players = arrayListOf(player1, player2))

        assertThat(game.checkWinner()).isEqualTo(player1)
    }


    //checkWinner tests
    @Test
    fun `checkWinner players different results second player wins`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.ACES)
        player1.saveScore(Set.TWOS)
        player1.saveScore(Set.THREES)
        player1.saveScore(Set.FOURS)
        player1.saveScore(Set.FIVES)
        player1.saveScore(Set.SIXES)
        player1.saveScore(Set.THREE_OF_A_KIND)
        player1.saveScore(Set.FOUR_OF_A_KIND)
        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.CHANCE)
        player1.saveScore(Set.FULL_HOUSE)
        player1.saveScore(Set.SMALL_STRAIGHT)
        player1.saveScore(Set.LARGE_STRAIGHT)



        player2.saveScore(Set.ACES)
        player2.saveScore(Set.TWOS)
        player2.saveScore(Set.THREES)
        player2.saveScore(Set.FOURS)
        player2.saveScore(Set.FIVES)
        player2.saveScore(Set.SIXES)
        player2.saveScore(Set.THREE_OF_A_KIND)
        player2.saveScore(Set.FOUR_OF_A_KIND)
        player2.saveScore(Set.YAHTZEE)
        player2.saveScore(Set.CHANCE)
        player2.saveScore(Set.FULL_HOUSE)
        player2.saveScore(Set.SMALL_STRAIGHT)
        player2.saveScore(Set.LARGE_STRAIGHT)

        player2.setScores[Set.SIXES] = 10

        val game = Game(players = arrayListOf(player1, player2))

        assertThat(game.checkWinner()).isEqualTo(player2)
    }


    @Test
    fun `checkWinner players same results returns first player`() {
        val player1 = Player()
        val player2 = Player()

        player1.saveScore(Set.ACES)
        player1.saveScore(Set.TWOS)
        player1.saveScore(Set.THREES)
        player1.saveScore(Set.FOURS)
        player1.saveScore(Set.FIVES)
        player1.saveScore(Set.SIXES)
        player1.saveScore(Set.THREE_OF_A_KIND)
        player1.saveScore(Set.FOUR_OF_A_KIND)
        player1.saveScore(Set.YAHTZEE)
        player1.saveScore(Set.CHANCE)
        player1.saveScore(Set.FULL_HOUSE)
        player1.saveScore(Set.SMALL_STRAIGHT)
        player1.saveScore(Set.LARGE_STRAIGHT)



        player2.saveScore(Set.ACES)
        player2.saveScore(Set.TWOS)
        player2.saveScore(Set.THREES)
        player2.saveScore(Set.FOURS)
        player2.saveScore(Set.FIVES)
        player2.saveScore(Set.SIXES)
        player2.saveScore(Set.THREE_OF_A_KIND)
        player2.saveScore(Set.FOUR_OF_A_KIND)
        player2.saveScore(Set.YAHTZEE)
        player2.saveScore(Set.CHANCE)
        player2.saveScore(Set.FULL_HOUSE)
        player2.saveScore(Set.SMALL_STRAIGHT)
        player2.saveScore(Set.LARGE_STRAIGHT)


        val game = Game(players = arrayListOf(player1, player2))

        assertThat(game.checkWinner()).isEqualTo(player1)
    }







}