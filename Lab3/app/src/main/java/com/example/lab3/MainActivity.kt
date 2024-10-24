package com.example.lab3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarInsets.left, systemBarInsets.top, systemBarInsets.right, systemBarInsets.bottom)
            insets
        }

        val inputPlayerName = findViewById<EditText>(R.id.edName)
        val infoTextView = findViewById<TextView>(R.id.tvText)
        val moraRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val playButton = findViewById<Button>(R.id.btnMora)
        val playerNameTextView = findViewById<TextView>(R.id.tvName)
        val winnerTextView = findViewById<TextView>(R.id.tvWinner)
        val playerMoraTextView = findViewById<TextView>(R.id.tvMyMora)
        val computerMoraTextView = findViewById<TextView>(R.id.tvTargetMora)

        playButton.setOnClickListener {
            val playerName = inputPlayerName.text.toString()
            if (playerName.isEmpty()) {
                infoTextView.text = "請輸入姓名"
                return@setOnClickListener
            }

            val playerMove = getPlayerMove(moraRadioGroup.checkedRadioButtonId)
            val computerMove = generateComputerMove()

            playerNameTextView.text = "名字\n$playerName"
            playerMoraTextView.text = "我方\n${moraToString(playerMove)}"
            computerMoraTextView.text = "電腦\n${moraToString(computerMove)}"

            val winner = determineWinner(playerMove, computerMove, playerName)
            winnerTextView.text = "勝利者\n$winner"
            infoTextView.text = if (winner == "平手") "平局，請再試一次！" else "恭喜你獲勝了！！！"
        }
    }

    private fun getPlayerMove(selectedId: Int): Int {
        return when (selectedId) {
            R.id.btnScissor -> 0
            R.id.btnStone -> 1
            else -> 2
        }
    }

    private fun generateComputerMove(): Int {
        return (0..2).random()
    }

    private fun moraToString(mora: Int): String {
        return when (mora) {
            0 -> "剪刀"
            1 -> "石頭"
            else -> "布"
        }
    }

    private fun determineWinner(playerMove: Int, computerMove: Int, playerName: String): String {
        return when {
            playerMove == computerMove -> "平手"
            (playerMove == 0 && computerMove == 2) ||
                    (playerMove == 1 && computerMove == 0) ||
                    (playerMove == 2 && computerMove == 1) -> playerName
            else -> "電腦"
        }
    }
}
