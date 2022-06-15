package com.michael.todolist

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ranquotesgenerator.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RanQuotesGenerator : AppCompatActivity() {

//    private lateinit var binding: RanQuotesGenerator

    var listQuotes = mutableListOf(
            R.string.quote1,
            R.string.quote2,
            R.string.quote3,
            R.string.quote4,
            R.string.quote5,
            R.string.quote6,
            R.string.quote7
    )

    var quoteNumber = 0
    var mainText = ""

    lateinit var motivQuotes : TextView

   lateinit var fab_newQuote: FloatingActionButton
   lateinit var tv_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranquotesgenerator)
        motivQuotes= findViewById<TextView>(R.id.motivQuotes)
        fab_newQuote= findViewById<FloatingActionButton>(R.id.fab_newQuote)
        tv_text= findViewById<TextView>(R.id.tv_text)
        quoteOnAppLoaded()
        clickNewQuote()
    }

    private fun clickNewQuote() {
        fab_newQuote.setOnClickListener {
            fab_newQuote.isEnabled = false

            if (quoteNumber == listQuotes.size) {
                quoteOnAppLoaded()
            } else {
                typeText(getString(listQuotes[quoteNumber]))
                ++quoteNumber
            }
        }
    }

    private fun quoteOnAppLoaded() {
        fab_newQuote.isEnabled = false
        quoteNumber = 0
        listQuotes.shuffle()
        typeText(getString(listQuotes[quoteNumber]))
        ++quoteNumber
    }

    private fun typeText(text: String) {
        mainText=""
        val textDelay = 50L

        GlobalScope.launch(Dispatchers.IO) {
            val sb = StringBuilder()
            val updatedText = ""

            for (i in text.indices) {
                mainText = sb.append(updatedText + text[i]).toString()
                Thread.sleep(textDelay)
            }
        }
        val handler = Handler()
        Log.d("Main", "Handler started")
        val runnable = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                tv_text.text = "$mainText|"
                handler.postDelayed(this,10)

                if (text == mainText) {
                    handler.removeCallbacks(this)
                    tv_text.text = mainText
                    fab_newQuote.isEnabled = true
                }
            }
        }
        handler.postDelayed(runnable,0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.exit1 -> {

                    // build alert dialog
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage("Are you sure you want to close this application?")
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                dialog, id -> finish()
                            })
                            // negative button text and action
                            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                            })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("AlertDialogExample")
                    // show alert dialog
                    alert.show()

                return true;
            }

            R.id.item1 -> { Toast.makeText(this, "Please tap on: 'More options'! (-->)", Toast.LENGTH_LONG).show()
                return true }

            R.id.item2 -> { Toast.makeText(this, "Motivational quotes; you're here!", Toast.LENGTH_LONG).show()
                val go1 = Intent(this@RanQuotesGenerator, RanQuotesGenerator::class.java)
                startActivity(go1)
                return true }

            R.id.item3 -> { Toast.makeText(this, "To-Do List!", Toast.LENGTH_LONG).show()
                val go2 = Intent(this@RanQuotesGenerator, MainActivity::class.java)
                startActivity(go2)
                return true }

            // לפרסם...

                R.id.nav_share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, mainText)
                startActivity(Intent.createChooser(shareIntent,"Share this quote!"))

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}