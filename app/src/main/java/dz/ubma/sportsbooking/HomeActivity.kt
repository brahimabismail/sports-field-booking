package dz.ubma.sportsbooking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // عرض تصميم الصفحة الرئيسية
        setContentView(R.layout.activity_home)

        // عناصر الصفحة
        val etSearchField = findViewById<EditText>(R.id.etSearchField)

        val btnFivePlayers = findViewById<Button>(R.id.btnFivePlayers)
        val btnSevenPlayers = findViewById<Button>(R.id.btnSevenPlayers)
        val btnElevenPlayers = findViewById<Button>(R.id.btnElevenPlayers)

        val btnBookField1 = findViewById<Button>(R.id.btnBookField1)
        val btnBookField2 = findViewById<Button>(R.id.btnBookField2)

        // اختيار نوع الملعب
        btnFivePlayers.setOnClickListener {
            showMessage("Selected: 5 vs 5")
        }

        btnSevenPlayers.setOnClickListener {
            showMessage("Selected: 7 vs 7")
        }

        btnElevenPlayers.setOnClickListener {
            showMessage("Selected: 11 vs 11")
        }

        // البحث عن ملعب
        etSearchField.setOnEditorActionListener { _, _, _ ->
            val search = etSearchField.text.toString().trim()

            if (search.isNotEmpty()) {
                showMessage("Searching for: $search")
            } else {
                showMessage("Enter a field name")
            }

            true
        }

        // أزرار الحجز
        btnBookField1.setOnClickListener {
            showMessage("Green Stadium selected")
        }

        btnBookField2.setOnClickListener {
            showMessage("Champions Field selected")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
