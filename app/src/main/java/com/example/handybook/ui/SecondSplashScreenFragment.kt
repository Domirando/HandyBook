package com.example.handybook.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.handybook.MyShared
import com.example.handybook.R
import com.example.handybook.databinding.FragmentSecondSplashScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondSplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondSplashScreenFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSecondSplashScreenBinding
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
        binding= FragmentSecondSplashScreenBinding.inflate(inflater,container,false)
        binding.progressBar.setVisibility(View.VISIBLE)
        val handler = Handler(Looper.getMainLooper())

        val shared = MyShared.getInstance(requireContext())
        var user = shared.getUser()



        if(user == null){
            handler.postDelayed({
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, SignFragment())
                    .commit()
            }, 2000)
        }else{
            handler.postDelayed({
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, MainFragment())
                    .commit()
            }, 2000)
        }
        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondSplashScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}