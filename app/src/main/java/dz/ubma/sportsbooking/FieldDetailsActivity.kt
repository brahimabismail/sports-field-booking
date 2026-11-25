package dz.ubma.sportsbooking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FieldDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_details)

        val tvFieldName = findViewById<TextView>(R.id.tvFieldName)
        val tvFieldLocation = findViewById<TextView>(R.id.tvFieldLocation)
        val tvFieldType = findViewById<TextView>(R.id.tvFieldType)
        val tvFieldDescription =
            findViewById<TextView>(R.id.tvFieldDescription)
        val tvFieldPrice = findViewById<TextView>(R.id.tvFieldPrice)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        val btnBookNow = findViewById<Button>(R.id.btnBookNow)

        // Receive field information
        val name = intent.getStringExtra("field_name") ?: "Football Field"
        val location = intent.getStringExtra("field_location") ?: "Unknown location"
        val type = intent.getStringExtra("field_type") ?: "5 vs 5"
        val price = intent.getStringExtra("field_price") ?: "3000 DA"
        val description = intent.getStringExtra("field_description")
            ?: "A modern football field with excellent facilities."

        // Display field information
        tvFieldName.text = name
        tvFieldLocation.text = location
        tvFieldType.text = "Players: $type"
        tvFieldPrice.text = price
        tvFieldDescription.text = description

        btnBack.setOnClickListener {
            finish()
        }

        btnBookNow.setOnClickListener {
            // Booking screen will be connected in the next step
        }
    }
}
