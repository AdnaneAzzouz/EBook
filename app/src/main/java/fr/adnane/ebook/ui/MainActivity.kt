package fr.adnane.ebook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import fr.adnane.ebook.databinding.ActivityMainBinding
import fr.adnane.ebook.localdata.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view:View = binding.root
        setContentView(view)
        setViewItems()
    }

    private fun setViewItems() {

        val storedSearch = SharedPreferencesManager().getSearchCriteria(this)
        if (storedSearch != null) {
            binding.searchEt.setText(storedSearch)
        }

        binding.searchBt.setOnClickListener {
            checkUserInput()
        }
        binding.favoritesBt.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    private fun checkUserInput() {
        if (binding.searchEt.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez effectuer une saisie", Toast.LENGTH_LONG).show()
            return
        }
        SharedPreferencesManager().saveSearchCriteria(binding.searchEt.text.toString(), this)
        val intent = Intent(this, SearchResultsActivity::class.java)
        intent.putExtra("search", binding.searchEt.text.toString())
        startActivity(intent)
    }
}