package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.handybook.R
import com.example.handybook.adapters.ViewPagerAdapter
import com.example.handybook.databinding.FragmentEBookBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EBookFragment : Fragment() {

    private var id: Int = 0
    lateinit var book: Book
    var list = listOf<String>("Tavsifi", "Sharhlar", "Iqtibslar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEBookBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

        api.getBookById(id).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null){
                    var item = response.body()!!
                    if (item.audio == null){
                        book = Book("item.audio",item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        binding.avatar.load(book.image)
                        binding.author.text = book.author
                        binding.name.text = book.name
                        binding.rating.text = book.reyting.toString()
                    }else{
                        book = Book(item.audio,item.author, item.count_page, item.description, item.file, item.id, item.image, item.lang, item.name, item.publisher, item.reyting, item.status, item.type_id, item.year)
                        binding.avatar.load(book.image)
                        binding.author.text = book.author
                        binding.name.text = book.name
                        binding.rating.text = book.reyting.toString()
                    }
                    Log.d("TAG", book.author)
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

//        binding.viewPager.adapter = ViewPagerAdapter(parentFragmentManager,lifecycle,id)
//        TabLayoutMediator(binding.tabLayout2, binding.viewPager){ tab, position ->
//            tab.text = list[position]
//        }.attach()

//        binding.read.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main, PDFViewerFragment.newInstance(id.toString(),""))
//                .commit()
//        }
        var bundle = Bundle()
        bundle.putInt("id",id)
        binding.read.setOnClickListener{
            var pdf = PdfViewFragment()
            pdf.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, pdf)
                .commit()
        }
        binding.rating.setOnClickListener {
            var details = ReviewFragment()
            bundle.putSerializable("id", id)
            details.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, details)
                .commit()
        }

        return binding.root
    }


}