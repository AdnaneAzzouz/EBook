package fr.adnane.ebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import fr.adnane.ebook.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view:View = binding.root
        setContentView(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        val author = intent.extras?.getString("author")
        val title = intent.extras?.getString("title")
        val image = intent.extras?.getString("thumbnail")
        val description = intent.extras?.getString("description")
        binding.authorDetailsTv.text = author
        binding.titleDetailsTv.text = title
        binding.descriptionDetailsTv.text = description
        setTitle(title)
        Glide.with(binding.bookDetailsIv.context).load(image)
            .into(binding.bookDetailsIv)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}