package com.example.emojiapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.emojiapp.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.next.setOnClickListener {
            val i = Intent(this@MainActivity2, NewActivity::class.java)
            val name = b.name.text.toString()
            val age = b.age.text.toString().toDoubleOrNull()
            val gender = when (b.gender.checkedRadioButtonId) {
                R.id.male -> "male"
                R.id.female -> "female"
                else -> "no gender"
            }
            if (age == null) {
                Toast.makeText(this, "Age must be a number", Toast.LENGTH_SHORT).show()
            } else {
                i.putExtra("name", name)
                i.putExtra("age", age)
                i.putExtra("gender", gender)
                startActivity(i)
            }
        }


        b.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Hide the keyboard when tapping outside of EditText
                hideKeyboard()
            }
            return@setOnTouchListener false
        }
    }


    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}