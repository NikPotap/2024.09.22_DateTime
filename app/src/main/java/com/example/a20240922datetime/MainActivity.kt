package com.example.a20240922datetime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.Month

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMainTB: androidx.appcompat.widget.Toolbar
    private lateinit var inputNameET: EditText
    private lateinit var inputLastnameET: EditText
    private lateinit var inputDayOfBirthET: EditText
    private lateinit var inputMonthOfBirthSPN: Spinner
    private lateinit var inputYearOfBirthET: EditText
    private lateinit var inputIconIV: ImageView
    private lateinit var saveItemBTN: Button
    private val GALLERY_REQUEST = 82302912
    private var imageUri: Uri? = null
    private var monthSize: Int = 0
    private var monthNum = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setVariables()
        setSupportActionBar(toolbarMainTB)
        saveItemBTN.setOnClickListener { view ->
//            closeKeyboard(view)
            setOfMonth(inputMonthOfBirthSPN.selectedItem.toString())
            val name = inputNameET.text.toString().lowercase()
            val lastName = inputLastnameET.text.toString().lowercase()
            var day = inputDayOfBirthET.text.toString()
            if (day.length == 1) day = "0$day"
            val month = monthNum
            val year = inputYearOfBirthET.text.toString()
            val birthDay = "$year-$month-$day"
            if (!testOfData(
                    this,
                    name,
                    lastName,
                    day,
                    monthSize,
                    birthDay,
                    year,
                    inputIconIV.tag.toString()
                )
            ) {
                return@setOnClickListener
            }
            if (day.length == 1) day = "0$day"
            val person = Person(
                "${name.first().uppercase()}${name.drop(1)}",
                "${lastName.first().uppercase()}${lastName.drop(1)}",
                birthDay,
                imageUri.toString()
            )
            val intent = Intent(this, PersonView::class.java)
            intent.putExtra("person", person)
            finish()
            startActivity(intent)
        }
        inputIconIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inputIconIV = findViewById(R.id.inputIconIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                imageUri = data?.data
                inputIconIV.setImageURI(imageUri)
                inputIconIV.tag = "true"
            }
        }
    }

    private fun closeKeyboard(view: View) {
        val imm =
            view.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setVariables() {
        toolbarMainTB = findViewById(R.id.toolbarMainTB)
        inputNameET = findViewById(R.id.inputNameET)
        inputLastnameET = findViewById(R.id.inputLastnameET)
        inputDayOfBirthET = findViewById(R.id.inputDayOfBirthET)
        inputMonthOfBirthSPN = findViewById(R.id.inputMonthOfBirthSPN)
        inputYearOfBirthET = findViewById(R.id.inputYearOfBirthET)
        inputIconIV = findViewById(R.id.inputIconIV)
        saveItemBTN = findViewById(R.id.saveItemBTN)
    }

    private fun setOfMonth(month: String) {
        when (month) {
            "января" -> {
                monthNum = "01"; monthSize = 31
            }

            "февраля" -> {
                monthNum = "02"; monthSize = 29
            }

            "марта" -> {
                monthNum = "03"; monthSize = 31
            }

            "апреля" -> {
                monthNum = "04"; monthSize = 30
            }

            "мая" -> {
                monthNum = "05"; monthSize = 31
            }

            "июня" -> {
                monthNum = "06"; monthSize = 30
            }

            "июля" -> {
                monthNum = "07"; monthSize = 31
            }

            "августа" -> {
                monthNum = "08"; monthSize = 31
            }

            "сентября" -> {
                monthNum = "09"; monthSize = 30
            }

            "октября" -> {
                monthNum = "10"; monthSize = 31
            }

            "ноября" -> {
                monthNum = "11"; monthSize = 30
            }

            "декабря" -> {
                monthNum = "12"; monthSize = 31
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.first_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}