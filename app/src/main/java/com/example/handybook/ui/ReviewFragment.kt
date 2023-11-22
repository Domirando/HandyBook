package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.handybook.MyShared
import com.example.handybook.R
import com.example.handybook.databinding.FragmentReviewBinding
import com.example.handybook.model.AddComment
import com.example.handybook.model.Book
import com.example.handybook.model.Comment
import com.example.handybook.model.User
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentReviewBinding.inflate(inflater, container, false)

//        binding.bookName.text=book.name+" romani sizga qanchalik manzur keldi?"
        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, CommentsFragment())
                .commit()
        }
        var rating = binding.ratingBar.rating
        if (rating.toString().toDouble()>3.0){
            binding.emoji.setImageResource(R.drawable.book)
        }
        val api = APIClient.getInstance().create(APIService::class.java)
        val shared = MyShared.getInstance(requireContext())
        var user = shared.getUser()
        var comment = AddComment(param1!!.toInt(), user!!.id, binding.review.text.toString(), 2)
        Log.d("user-id", user!!.id.toString())


        binding.send.setOnClickListener{
            api.addComment(comment).enqueue(object : Callback<AddComment> {
                override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                    Log.d("yubordi:", response.body().toString())
                }
                override fun onFailure(call: Call<AddComment>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }
        binding.cancel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }
        binding.back.setOnClickListener {
            var bundle = Bundle()
            var details = DetailsFragment()
            bundle.putInt("book", param1!!.toInt())
            details.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, details)
                .commit()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OqilayotganFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

