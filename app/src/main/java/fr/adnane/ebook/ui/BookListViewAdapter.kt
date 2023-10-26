package fr.adnane.ebook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import fr.adnane.ebook.R
import fr.adnane.ebook.models.Items

class BookListViewAdapter(var books: MutableList<Items>, var bookItemCallback: BookItemCallback) : Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_items, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position], bookItemCallback)
    }

}