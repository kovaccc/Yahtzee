package com.example.yahtzee.adapters.viewHolders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.yahtzee.R
import com.example.yahtzee.adapters.DiceRecyclerViewAdapter
import com.example.yahtzee.databinding.ItemDiceBinding
import com.example.yahtzee.getMyColor
import com.example.yahtzee.getMyDrawable
import com.example.yahtzee.model.Dice
import com.example.yahtzee.model.DieResult


private const val TAG = "DiceViewHolder"

class DiceViewHolder(private val binding: ItemDiceBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dice: Dice, listener: DiceRecyclerViewAdapter.OnDiceClickListener ) {

        Log.d(TAG, "bind: starts with $dice and listener $listener")

        binding.ivDice.setImageResource(when(dice.result) {
            DieResult.ONE -> R.drawable.icons8_dice_one_32
            DieResult.TWO -> R.drawable.icons8_dice_two_32
            DieResult.THREE -> R.drawable.icons8_dice_three_32
            DieResult.FOUR -> R.drawable.icons8_dice_four_32
            DieResult.FIVE -> R.drawable.icons8_dice_five_32
            DieResult.SIX -> R.drawable.icons8_dice_six_32
            DieResult.NOT_ROLLED ->  R.drawable.placeholder
        })


        if(dice.savedDie) {
            binding.ivDice.background = itemView.context.getMyDrawable(R.drawable.et_corner_clicked)
        }
        else {
            binding.ivDice.setBackgroundColor(itemView.context.getMyColor(android.R.color.transparent)) // remove corner, you could also background = null
        }

        binding.ivDice.setOnClickListener {
            listener.onSaveClick(dice)
        }

        Log.d(TAG, "bind: ends with $dice and listener $listener")
    }

}