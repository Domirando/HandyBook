package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.handybook.R
import com.example.handybook.databinding.FragmentReviewBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itteacher.mybook.moedel.AddComment
import uz.itteacher.mybook.moedel.Comment

class ReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentReviewBinding.inflate(inflater, container, false)

        var  book_id = arguments?.getSerializable("id") as Book
//        binding.bookName.text=book.name+" romani sizga qanchalik manzur keldi?"
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        var rating = binding.ratingBar.rating
        if (rating.toString().toDouble()>3.0){
            binding.emoji.setImageResource(R.drawable.book)
        }
        binding.send.setOnClickListener{
            val c = AddComment(book_id = 2, user_id = 1, reyting = rating.toString().toDouble(), text = binding.review.text.toString())
            val api = APIClient.getInstance().create(APIService::class.java)
            api.addComment(c).enqueue(object : Callback<AddComment> {
                override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main,MainFragment())
                        .commit()
                }

                override fun onFailure(call: Call<AddComment>, t: Throwable) {
                    Log.d("failure", "onFailure: $t")
                }
            })
            api.getBookComment(1).enqueue(object :Callback<List<Comment>>{
                override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                    Log.d("comment", response.toString())
                }

                override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    Log.d("Asdf", "onFailure: $t")
                }

                    })
        }
        binding.send.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main,MainFragment())
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
                }
            }
    }
}