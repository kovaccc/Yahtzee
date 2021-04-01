package com.example.yahtzee.dialogs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.yahtzee.R
import com.example.yahtzee.databinding.DialogNewGameBinding


private const val TAG = "NewGameDialog"

const val NEW_GAME_DIALOG_ID = "ID"
const val NEW_GAME_DIALOG_PLAYERS_NAME = "PLAYERS NAME"

class NewGameDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewGameBinding
    private var dialogId = 0

    interface NewGameDialogEvents {
        fun onCreateDialogResult(dialogId: Int, args: Bundle)
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.NewGameDialogStyle)

        val arguments = arguments

        if(arguments != null) {

            dialogId = arguments.getInt(NEW_GAME_DIALOG_ID)
        }

        Log.d(TAG, "onCreate: ends, arguments: dialog id $dialogId")

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: starts")
        binding = DialogNewGameBinding.inflate(inflater, container, false)

        Log.d(TAG, "onCreateView: ends")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: starts")
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle(R.string.action_new_game)

        with(binding) {
            createButton.setOnClickListener {
                when {
                    etFirstPlayerName.text?.isEmpty()!! -> {
                        etFirstPlayerName.error = getString(R.string.error_valid_nickname)
                    }
                    etSecondPlayerName.text?.isEmpty()!! -> {
                        etSecondPlayerName.error = getString(R.string.error_valid_nickname)
                    }
                    else -> {

                        val args = Bundle().apply {
                            putStringArray(NEW_GAME_DIALOG_PLAYERS_NAME,
                                arrayOf(etFirstPlayerName.text.toString(), etSecondPlayerName.text.toString()))
                        }
                        Log.d(TAG, "setOnClickListener: starts with $args")
                        (activity as NewGameDialogEvents).onCreateDialogResult(dialogId, args)
                        dismiss()
                    }
                }
            }

            cancelButton.setOnClickListener { dismiss()}
        }



        Log.d(TAG, "onViewCreated: ends")

    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach called: context is $context")
        super.onAttach(context)

        // Activities using this dialog must implement its callbacks.
        if (context !is NewGameDialogEvents) {
            throw RuntimeException("$context must implement NewGameDialog.NewGameDialogEvents interface")
        }
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach called")
        super.onDetach()
    }

}