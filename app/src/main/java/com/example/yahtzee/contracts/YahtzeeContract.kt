package com.example.yahtzee.contracts



object YahtzeeContract {

    object GameValues {
        const val SETS_ALL_COMPLETED = 13 // max number of sets
        const val ROLLING_ALLOWED = 3 // max rolling for player
        const val BONUS_IS_ACHIEVED = 63 // limit for which you get bonus points
        const val BONUS_EXTRA_POINTS = 35 // bonus points if you reach bonus limit
        const val ZERO_POINTS = 0
        const val FULL_HOUSE_POINTS = 25
        const val SMALL_STRAIGHT_POINTS = 30
        const val LARGE_STRAIGHT_POINTS = 40
        const val YAHTZEE_POINTS = 50
    }

}