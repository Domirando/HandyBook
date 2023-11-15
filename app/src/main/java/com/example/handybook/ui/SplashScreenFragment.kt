package com.example.handybook.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.handybook.R
import com.example.handybook.databinding.FragmentSplashScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashScreenFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var progressStatus = 0
    private var handler = Handler()
    private var limit=0
    private lateinit var binding: FragmentSplashScreenBinding
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
        binding= FragmentSplashScreenBinding.inflate(inflater,container,false)
        //binding.button.setOnClickListener {
        Thread(Runnable {
            while (progressStatus < 100){
                // update progress status
                progressStatus +=1

                // sleep the thread for 100 milliseconds
                Thread.sleep(50)

                // update the progress bar
                handler.post {
                    binding.progressBar.progress = progressStatus
                    binding.pr.text = "$progressStatus"
                    limit=progressStatus
                }
            }
            if(limit==100){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, SecondSplashScreenFragment())
                    .commit()
            }
        }).start()

        //}

        return binding.root
    }

}