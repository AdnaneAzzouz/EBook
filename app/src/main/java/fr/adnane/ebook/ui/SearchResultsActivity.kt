package fr.adnane.ebook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.adnane.ebook.R
import fr.adnane.ebook.databinding.ActivitySearchResultsBinding
import fr.adnane.ebook.localdata.SharedPreferencesManager
import fr.adnane.ebook.models.GoogleApiResponse
import fr.adnane.ebook.models.Items
import fr.adnane.ebook.service.ResultsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        title = "Résultat de la recherche"
         val service: ResultsApi.ResultsService =
            ResultsApi().getClient().create(ResultsApi.ResultsService::class.java)
        val call: Call<GoogleApiResponse> =
            service.getResults(intent.getStringExtra("search").toString())
        call.enqueue(object : Callback<GoogleApiResponse> {
            override fun onResponse(
                call: Call<GoogleApiResponse>,
                response: Response<GoogleApiResponse>
            ) {
                processResponse(response)
            }

            override fun onFailure(call: Call<GoogleApiResponse>, t: Throwable) {
                processFailure(t)
            }
        })
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
        Log.d("Erreur", t.message.toString())
    }

    private fun processResponse(response: Response<GoogleApiResponse>) {

        if (response.body() != null) {
            val body = response.body()
            if (body?.items?.isNotEmpty() == true) {
                val adapter = BookListViewAdapter(
                    body.items,
                    object : BookItemCallback {
                    override fun onCellClick(volume: Items) {
                        gotoNextActivity(volume)
                    }

                    override fun onSaveBook(volume: Items) {
                        saveBook(volume)
                    }

                })
                val recyclerView = findViewById<RecyclerView>(R.id.books_result_rv)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            }
        }
    }

    private fun saveBook(volume: Items) {
        if (SharedPreferencesManager().saveBook(volume, this)){
            Toast.makeText(this,"Enregistrement bien effectué", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"Cette photo est déjà dans vos favoris", Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoNextActivity(volume: Items) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("author", volume.volumeInfo?.authors?.get(0))
        intent.putExtra("title", volume.volumeInfo?.title)
        intent.putExtra("thumbnail", volume.volumeInfo?.imageLinks?.thumbnail)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}