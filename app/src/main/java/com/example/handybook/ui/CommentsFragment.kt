package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handybook.R
import com.example.handybook.adapters.CommentsAdapter
import com.example.handybook.databinding.FragmentCommentsBinding
import com.example.handybook.model.Comment
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var comments = mutableListOf<Comment>()

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
        val binding = FragmentCommentsBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)
        Log.d("comment param", param1.toString())
        api.getBookComment(param1.toString().toInt()).enqueue(object: Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful && response.body()!= null){
                    comments = response.body()!!.toMutableList()
                    binding.commentsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.commentsRv.adapter = CommentsAdapter(comments)

                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("TAG", "onFailure: $")
            }
        })

        binding.comment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ReviewFragment.newInstance(param1!!,""))
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
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}