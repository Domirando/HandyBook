
package com.example.handybook.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.MyShared
import com.example.handybook.R
import com.example.handybook.databinding.FragmentLoginBinding
import com.example.handybook.model.Login
import com.example.handybook.model.User
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
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentLoginBinding

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
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.registrationChooseId.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, RegistrationFragment())
                .commit()
        }
        val api = APIClient.getInstance().create(APIService::class.java)
        binding.loginBtnId.setOnClickListener {
            var username=binding.usernameEdittextId.text.trim()
            var password=binding.passwordEdittextId.text?.trim()
            val l= Login(username.toString(),password.toString())
            if (l.password == "" || l.username == "") return@setOnClickListener
            api.login(l).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(requireContext(), "Noto'g'ri username yoki parol", Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (binding.loginRememberMe.isChecked) {
                        var user: User = response.body()!!
                        val shared = MyShared.getInstance(requireContext())
                        shared.setUser(user)

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.main, MainFragment())
                            .commit()
                    } else {
                        var user: User = response.body()!!
                        val shared = MyShared.getInstance(requireContext())
                        shared.setUser(user)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.main, MainFragment())
                            .commit()
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "$t")
                }

            })
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}