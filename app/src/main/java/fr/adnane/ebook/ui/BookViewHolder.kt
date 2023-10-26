package fr.adnane.ebook.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.adnane.ebook.R
import fr.adnane.ebook.models.Items

class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var thumbnailIV: ImageView
    private var authorTV: TextView
    private var titleTV: TextView
    private var containerCL: ConstraintLayout
    private var bookmark: ImageView

    init {
        thumbnailIV = itemView.findViewById(R.id.thumbnail_list_iv)
        titleTV = itemView.findViewById(R.id.title_list_tv)
        authorTV = itemView.findViewById(R.id.author_list_tv)
        containerCL = itemView.findViewById(R.id.container)
        bookmark = itemView.findViewById(R.id.bookmark)
    }

    fun bind(
        volume: Items,
        bookItemCallback: BookItemCallback
    ) {
        authorTV.text = volume.volumeInfo?.authors?.get(0)
        titleTV.text = volume.volumeInfo?.title
        Glide.with(thumbnailIV.context).load(volume.volumeInfo?.imageLinks?.thumbnail)
            .into(thumbnailIV)
        containerCL.setOnClickListener {
            bookItemCallback.onCellClick(volume)
        }
        bookmark.setOnClickListener {
            bookItemCallback.onSaveBook(volume)
        }
    }
}
