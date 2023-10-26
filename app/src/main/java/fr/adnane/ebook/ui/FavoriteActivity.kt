package fr.adnane.ebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import fr.adnane.ebook.R
import fr.adnane.ebook.databinding.ActivityFavoriteBinding
import fr.adnane.ebook.localdata.SharedPreferencesManager
import fr.adnane.ebook.models.Items

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(R.string.favorites)
        displayBookList(SharedPreferencesManager().getLocalBookStorage(this).localBooks)
    }


    fun displayBookList(books: MutableList<Items>) {
        val adapter = BookListViewAdapter(books, object : BookItemCallback {
            override fun onCellClick(volume: Items) {
            }
            override fun onSaveBook(volume: Items) {
                // here in favorites list we should rather delete
                deleteBook(volume)
            }
        })
        binding.bookFavoriteRv.adapter = adapter
        binding.bookFavoriteRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun deleteBook(volume: Items) {
        SharedPreferencesManager().deleteBook(volume, this)
        displayBookList(SharedPreferencesManager().getLocalBookStorage(this).localBooks)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}