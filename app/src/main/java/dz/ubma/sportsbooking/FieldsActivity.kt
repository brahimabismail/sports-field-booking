package dz.ubma.sportsbooking

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
        val btnDetailsChampions = findViewById<Button>(R.id.btnDetailsChampions)
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

        // Field details
        btnDetailsGreen.setOnClickListener {
            showMessage("Green Stadium selected")
        }

        btnDetailsChampions.setOnClickListener {
            showMessage("Champions Field selected")
        }

        btnDetailsElite.setOnClickListener {
            showMessage("Elite Football Arena selected")
        }

        // Back
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
