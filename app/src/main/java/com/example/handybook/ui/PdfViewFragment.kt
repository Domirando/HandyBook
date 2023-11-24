package com.example.handybook.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.handybook.databinding.FragmentPdfViewBinding


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
        // Inflate the layout for this fragment
        var pdf_url = Uri.parse(url)
        val binding = FragmentPdfViewBinding.inflate(inflater, container, false)
        binding.pdfView.fromUri(pdf_url)
        val intent = Intent(Intent.ACTION_VIEW, pdf_url)
        val context: Context = binding.pdfView.context
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            Log.d("hey", "hey")
        } else {
            Log.w("TAG", "No activity found for URI: $pdf_url")
        }
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