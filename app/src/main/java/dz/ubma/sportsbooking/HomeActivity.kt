package dz.ubma.sportsbooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        // Search
        val etSearchField = findViewById<EditText>(R.id.etSearchField)

        // Categories
        val btnFivePlayers = findViewById<Button>(R.id.btnFivePlayers)
        val btnSevenPlayers = findViewById<Button>(R.id.btnSevenPlayers)
        val btnElevenPlayers = findViewById<Button>(R.id.btnElevenPlayers)

        // Booking buttons
        val btnBookField1 = findViewById<Button>(R.id.btnBookField1)
        val btnBookField2 = findViewById<Button>(R.id.btnBookField2)

        // Open Fields List
        fun openFields() {
            val intent = Intent(this, FieldsActivity::class.java)
            startActivity(intent)
        }

        // Categories
        btnFivePlayers.setOnClickListener {
            openFields()
        }

        btnSevenPlayers.setOnClickListener {
            openFields()
        }

        btnElevenPlayers.setOnClickListener {
            openFields()
        }

        // Search
        etSearchField.setOnEditorActionListener { _, _, _ ->
            val search = etSearchField.text.toString().trim()

            if (search.isNotEmpty()) {
                openFields()
            } else {
                showMessage("Enter a field name")
            }

            true
        }

        // Booking buttons
        btnBookField1.setOnClickListener {
            openFields()
        }

        btnBookField2.setOnClickListener {
            openFields()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
