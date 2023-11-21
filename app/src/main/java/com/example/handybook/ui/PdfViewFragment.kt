package com.example.handybook.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import coil.load
import com.example.handybook.R
import com.example.handybook.databinding.FragmentEBookBinding
import com.example.handybook.databinding.FragmentLoginBinding
import com.example.handybook.databinding.FragmentPdfViewBinding
import com.example.handybook.model.Book
import com.example.handybook.model.RetrievePDFFromURL
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.github.barteksc.pdfviewer.PDFView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PdfViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PdfViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var pdf_url = ""
        val binding = FragmentPdfViewBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)
        api.getBookById(id).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null){
                    var item = response.body()!!
                    pdf_url = item.file
                    Log.d("filee", pdf_url)
                }
            }
            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
        Log.d("id", id.toString())
        Log.d("file", pdf_url)
        RetrievePDFFromURL(binding.pdfView).execute("http://handybook.uz/frontend/web/file/701697625957.pdf")
        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PdfViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}