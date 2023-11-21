package com.example.handybook.ui

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.handybook.R
import com.example.handybook.databinding.FragmentAudioBookBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Response
import javax.security.auth.callback.Callback

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class AudioBookFragment : Fragment() {

    private var id: Int = 0
    lateinit var book: Book
    lateinit var runnable:Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("id")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAudioBookBinding.inflate(inflater ,container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

//        api.getBookById(id).enqueue(object : Callback<Book> {
//            override fun onResponse(call: Call<Book>, response: Response<Book>) {
//                if (response.isSuccessful && response.body() != null){
//                    val item = response.body()!!
//                    if (item.audio == null){
//                        book = Book("item.audio",item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
//                        binding.avatar.load(book.image){
//                            transformations(CircleCropTransformation())
//                        }
//                        binding.name.text = book.name
//                        binding.rating.text = book.reyting.toString()
//                        binding.author.text = book.author
//                    }else{
//                        book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
//                        binding.avatar.load(book.image){
//                            transformations(CircleCropTransformation())
//                        }
//                        binding.name.text = book.name
//                        binding.rating.text = book.reyting.toString()
//                        binding.author.text = book.author
//
//                        val mp = MediaPlayer.create(requireContext(), Uri.parse(book.audio))
//
//                        binding.seekbar.progress = 0
//                        binding.seekbar.max = mp.duration
//
//                        binding.play.setOnClickListener {
//                            if (!mp.isPlaying){
//                                mp.start()
//                                binding.play.setImageResource(R.drawable.baseline_pause_24)
//                            }else{
//                                mp.pause()
//                                binding.play.setImageResource(R.drawable.baseline_play_arrow_24)
//                            }
//                        }
//
//                        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//                            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                                if (mp!= null && fromUser){
//                                    mp.seekTo(progress)
//                                    Log.d("TAG", progress.toString())
//                                }
//                            }
//
//                            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                                TODO("Not yet implemented")
//                            }
//
//                            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                                TODO("Not yet implemented")
//                            }
//                        })
//
//                        runnable = Runnable {
//                            binding.seekbar.progress = mp.currentPosition
//                            handler.postDelayed(runnable,1000)
//                        }
//                        handler.postDelayed(runnable,1000)
//
//                        mp.setOnCompletionListener {
//                            binding.play.setImageResource(R.drawable.baseline_play_arrow_24)
//                            binding.seekbar.progress = 0
//                        }
//                    }
//
//
//                }
//            }
//
//            override fun onFailure(call: Call<Book>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//        })


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioBookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AudioBookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
