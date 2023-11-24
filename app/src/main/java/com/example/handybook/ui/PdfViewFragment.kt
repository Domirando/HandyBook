package com.example.handybook.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.handybook.MyViewModel
import com.example.handybook.databinding.FragmentHomeBinding
import com.example.handybook.databinding.FragmentPdfViewBinding

import com.example.handybook.model.createFileFromInputStream
import com.example.handybook.model.readFileFromStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.URL


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
    private var url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString("url").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPdfViewBinding.inflate(inflater, container, false)
        val fileName = getFileName(url)
        var file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        binding.pdfView.fromFile(file).load()

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
    fun fileMaker(url:String): File {
        return File(url)
    }
    fun getFileName(filePath: String): String {
        val lastIndex = filePath.lastIndexOf('/')
        return if (lastIndex != -1) {
            // Extracting the substring after the last '/'
            filePath.substring(lastIndex + 1)
        } else {
            // If no '/' found, return the whole string as the file name
            filePath
        }
    }
}