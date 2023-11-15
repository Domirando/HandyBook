package com.example.handybook.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.R
import com.example.handybook.databinding.FragmentRegistrationBinding
import com.example.handybook.model.UserReg
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
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRegistrationBinding
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
        binding= FragmentRegistrationBinding.inflate(inflater,container,false)
        binding.bacKBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, SignFragment())
                .commit()
        }
        val username:String=binding.username.toString()
        val full_name:String=binding.fullName.text.toString()
        val email:String=binding.emailEdittextId.text.toString()
        val password:String=binding.passwordEdittextId.text.toString()
        val api = APIClient.getInstance().create(APIService::class.java)
        binding.regBtnId.setOnClickListener {
            if (check_info()) {
                Log.d("bosildimi", "ha")
                val registerInfo = UserReg(username, full_name, email, password )
                api.register(registerInfo).enqueue(object : Callback<UserReg> {
                    override fun onResponse(call: Call<UserReg>, response: Response<UserReg>) {
                        Log.d("kod", response.code().toString())
                        if (response.code() == 201) {
                            Log.d("server", "oldi")
                            Toast.makeText(context, "Registration success!", Toast.LENGTH_SHORT)
                                .show()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main, LoginFragment())
                                .commit()
                        }else{
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main, HomeFragment())
                                .commit()
                        }
                    }
                    override fun onFailure(call: Call<UserReg>, t: Throwable) {
                        Log.d("server", "onFailure: $t")
                        Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
        return binding.root
    }

    fun check_info(): Boolean {
        if (binding.passwordEdittextId.text.toString()!=(binding.cPasswordEdittextId.text.toString())) {
            Toast.makeText(requireContext(),"Parollar mos kelmadi",Toast.LENGTH_SHORT).show()
            binding.passwordEdittextId.error="Parollar mos kelmadi"
            binding.cPasswordEdittextId.error="Parollar mos kelmadi"
            return false
        }
        if (binding.passwordEdittextId.text!!.count()<8 && binding.cPasswordEdittextId.text!!.count()<8){
            Toast.makeText(requireContext(),"Parol 8 ta belgidan kam bolmasligi kerak",Toast.LENGTH_SHORT).show()
            binding.passwordEdittextId.error="Parol 8 ta belgidan kam bolmasligi kerak"
            binding.cPasswordEdittextId.error="Parol 8 ta belgidan kam bolmasligi kerak"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEdittextId.text.toString()).matches()){
            Toast.makeText(requireContext(),"Email xato",Toast.LENGTH_SHORT).show()
            binding.emailEdittextId.error="Emailni to'g'ri kiriting"
            return false
        }
        if(binding.username.text.toString().equals("")){
            Toast.makeText(requireContext(),"Ismingizni kiriting",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.passwordEdittextId.text.equals("")){
            Toast.makeText(requireContext(),"Parol kiriting",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.cPasswordEdittextId.text.equals("")){
            Toast.makeText(requireContext(),"Parolni tasdiqlash uchun yana bir bor kiriting",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.emailEdittextId.text.equals("")){
            Toast.makeText(requireContext(),"Email manzilingizni kiriting",Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.fullName.text.equals("")){
            Toast.makeText(requireContext(),"Familiyangizni kiriting",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    companion object {
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