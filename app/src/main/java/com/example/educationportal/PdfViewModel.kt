package com.example.educationportal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PdfViewModel : ViewModel() {
    var bookData: MutableLiveData<BookModel> = MutableLiveData()

    fun updateBookData(bookModel: BookModel) {
        bookData.value = bookModel
    }
}