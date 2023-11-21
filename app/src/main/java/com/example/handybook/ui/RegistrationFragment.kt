package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.databinding.FragmentRegistrationBinding
import com.example.handybook.model.User
import com.example.handybook.model.UserReg
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.example.handybook.utils.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
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
    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegistrationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        var sharedPrefHelper= SharedPrefHelper.getInstance(requireContext())


        val api = APIClient.getInstance().create(APIService::class.java)
        binding.regBtnId.setOnClickListener {
            if (check()){
                val signUp = UserReg(
                    binding.username.text.toString(),
                    binding.fullName.text.toString(),
                    binding.emailEdittextId.text.toString(),
                    binding.passwordEdittextId.text.toString()
                )
                Log.d("USER", signUp.toString())
                api.signup(signUp).enqueue(object: Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Log.d("CODE",response.code().toString())
                        var user= User(response.body()!!.access_token, response.body()!!.id,
                            response.body()!!.username)
                        sharedPrefHelper.setUser(user)


                        Log.d("USER",user.toString())
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("ERROR",t.toString())
                    }

                })
            }


        }

//        binding.bacKBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
//        }



        return binding.root
    }
    fun check(): Boolean {
        if (!binding.ischecked.isChecked){
            Toast.makeText(requireContext(),"Tasdiqlashni bosing",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.passwordEdittextId.text.toString()!=(binding.passwordEdittextId.text.toString())) {
            Toast.makeText(requireContext(),"Parollar mos kelmayapti",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.passwordEdittextId.text!!.count()<8 && binding.passwordEdittextId.text!!.count()<8){
            Toast.makeText(requireContext(),"Parol 8 ta belgidan kam bolmasligi kerak",Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEdittextId.text.toString()).matches()){
            Toast.makeText(requireContext(),"Email hato",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}