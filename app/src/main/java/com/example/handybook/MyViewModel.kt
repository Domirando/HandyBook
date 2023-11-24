package com.example.handybook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handybook.ui.downloadFileFromApi
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    fun downloadFile(url:String) {
        // Replace with your download logic using viewModelScope.launch
        viewModelScope.launch {
            // Call the download function here
            val regex = Regex("""[^/]+(?=/$|$)""")
            val fileName = regex.find(url)
            val success = downloadFileFromApi(url, fileName?.value ?: "")

            if (success) {
                Log.d("down?", "i did it!")
            } else {

            }
        }
    }
}
