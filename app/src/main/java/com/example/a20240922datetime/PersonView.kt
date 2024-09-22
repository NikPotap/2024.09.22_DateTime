package com.example.a20240922datetime

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period

@SuppressLint("NewApi")
class PersonView : AppCompatActivity() {
    private lateinit var toolbarSecondTB: Toolbar
    private lateinit var outputIconIV: ImageView
    private lateinit var outputNameTV: TextView
    private lateinit var outputLastNameTV: TextView
    private lateinit var outputAgeTV: TextView
    private lateinit var outputBeforeBirthdayTV: TextView
    private lateinit var person: Person
    private var birthDay = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setVariables()
        setSupportActionBar(toolbarSecondTB)
        person = intent?.getSerializableExtra("person") as Person
        outputIconIV.setImageURI(Uri.parse(person.photo))
        outputNameTV.text = person.name
        outputLastNameTV.text = person.lastName
        birthDay = person.dateOfBirth
        val age = getAge(birthDay)
        outputAgeTV.text = "Возраст: $age"
        val interval =
            Period.between(LocalDate.now(), LocalDate.parse(birthDay).plusYears((age + 1).toLong()))
        var months = ""
        if (interval.months != 0) months = " ${interval.months} мес."
        var days = ""
        if (interval.days != 0) days = " ${interval.days} дн."
        outputBeforeBirthdayTV.text = "До дня рождения осталось$months$days"
    }

    private fun setVariables() {
        toolbarSecondTB = findViewById(R.id.toolbarSecondTB)
        outputIconIV = findViewById(R.id.outputIconIV)
        outputNameTV = findViewById(R.id.outputNameTV)
        outputLastNameTV = findViewById(R.id.outputLastNameTV)
        outputAgeTV = findViewById(R.id.outputAgeTV)
        outputBeforeBirthdayTV = findViewById(R.id.outputBeforeBirthdayTV)
    }

    fun getAge(birthDate: String): Int {
        val thisYear = LocalDate.now().year
        val birthYear = birthDate.slice(0..3).toInt()
        var age = thisYear - birthYear
        val tempDate = "${birthYear + age}${birthDay.slice(4..9)}"
        if (LocalDate.now().isBefore(LocalDate.parse(tempDate))) return --age
        return age
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.second_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
