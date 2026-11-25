package dz.ubma.sportsbooking

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FieldsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fields)

        // Search
        val etFieldSearch = findViewById<EditText>(R.id.etFieldSearch)

        // Filters
        val btnFilterAll = findViewById<Button>(R.id.btnFilterAll)
        val btnFilterFive = findViewById<Button>(R.id.btnFilterFive)
        val btnFilterSeven = findViewById<Button>(R.id.btnFilterSeven)

        // Details buttons
        val btnDetailsGreen = findViewById<Button>(R.id.btnDetailsGreen)
        val btnDetailsChampions =
            findViewById<Button>(R.id.btnDetailsChampions)
        val btnDetailsElite = findViewById<Button>(R.id.btnDetailsElite)

        // Back button
        val btnBack = findViewById<TextView>(R.id.btnBack)

        // Search action
        etFieldSearch.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                val search = etFieldSearch.text.toString().trim()

                if (search.isEmpty()) {
                    showMessage("Enter a field name")
                } else {
                    showMessage("Searching for: $search")
                }

                true
            } else {
                false
            }
        }

        // Filters
        btnFilterAll.setOnClickListener {
            showMessage("All fields selected")
        }

        btnFilterFive.setOnClickListener {
            showMessage("5 vs 5 fields selected")
        }

        btnFilterSeven.setOnClickListener {
            showMessage("7 vs 7 fields selected")
        }

        // Open field details
        btnDetailsGreen.setOnClickListener {
            openFieldDetails(
                "Green Stadium",
                "City Center",
                "5 vs 5",
                "3000 DA",
                "A modern football field with quality artificial grass."
            )
        }

        btnDetailsChampions.setOnClickListener {
            openFieldDetails(
                "Champions Field",
                "New District",
                "7 vs 7",
                "4000 DA",
                "A spacious football field suitable for competitive matches."
            )
        }

        btnDetailsElite.setOnClickListener {
            openFieldDetails(
                "Elite Football Arena",
                "North District",
                "11 vs 11",
                "6000 DA",
                "A large football stadium with professional facilities."
            )
        }

        // Back
        btnBack.setOnClickListener {
            finish()
        }
    }

    // Send field information to FieldDetailsActivity
    private fun openFieldDetails(
        name: String,
        location: String,
        type: String,
        price: String,
        description: String
    ) {

        val intent = Intent(this, FieldDetailsActivity::class.java)

        intent.putExtra("field_name", name)
        intent.putExtra("field_location", location)
        intent.putExtra("field_type", type)
        intent.putExtra("field_price", price)
        intent.putExtra("field_description", description)

        startActivity(intent)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
