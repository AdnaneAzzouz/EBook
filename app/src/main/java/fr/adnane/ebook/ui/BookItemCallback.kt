package fr.adnane.ebook.ui

import fr.adnane.ebook.models.Items

interface BookItemCallback {
    fun onCellClick(volume: Items)
    fun onSaveBook(volume: Items)
}
