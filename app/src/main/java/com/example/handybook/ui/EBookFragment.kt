package com.example.handybook.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import android.os.Environment
import android.util.Log
import com.example.handybook.MyViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.io.*

class EBookFragment() : Fragment() {
    var downloading = false
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
        val destination = File(requireContext().externalCacheDir, "file.pdf")
        api.getBookById(id).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful && response.body() != null){
                    Log.d("down res", "yep")
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

        binding.viewPager.adapter = ViewPagerAdapter(parentFragmentManager,lifecycle,id)
        TabLayoutMediator(binding.tabLayout2, binding.viewPager){ tab, position ->
            tab.text = list[position]
        }.attach()


        var bundle = Bundle()
        bundle.putInt("id",id)

        binding.read.setOnClickListener{
            val regex = Regex("""[^/]+(?=/$|$)""")
            val fileName = regex.find(book.file)
            MyViewModel().downloadFile(book.file)
            var fileUri = findFileByName(fileName?.value.toString())
//            val fileUri = getFileUriFromInternalStorage(requireContext(), fileName?.value.toString())
            fileUri?.let {
                openFile(it)
            }
        }
        binding.linearLayout5.setOnClickListener {
            var details = ReviewFragment()
            bundle.putSerializable("id", id)
            details.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, details)
                .commit()
        }

        return binding.root
    }
//    }
    private fun openFile(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf") // Adjust MIME type as per your file type
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
//            var bundle = Bundle()
//            bundle.putString("url", uri.toString())
//            var pdf = PdfViewFragment()
//            pdf.arguments = bundle
//            Log.d("hey1", "hey")
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.frame_container, pdf)
//                .commit()
//            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.d("err", e.message.toString())
        }
        Log.d("down uri", uri.toString())
    }
    private fun findFileByName(fileName: String): Uri? {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val files = directory.listFiles()

        files?.forEach { file ->
            if (file.name == fileName) {
                return Uri.fromFile(file)
            }
        }

        return null
    }
    private fun getFileUriFromInternalStorage(context: Context, fileName: String): Uri? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } else {
            null
        }
    }
}


suspend fun downloadFileFromApi(url: String, fileName: String): Boolean {
    return withContext(Dispatchers.IO) {
        var successful = false
        try {
            val connection = URL(url).openConnection()
            connection.connect()
            val input = BufferedInputStream(connection.getInputStream())
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val outputFile = File(directory, fileName)
            val output = FileOutputStream(outputFile)
            val data = ByteArray(1024)
            var total: Long = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            successful = true
        } catch (e: Exception) {
            Log.e("DownloadError", "Error downloading file: ${e.message}")
        }
        successful
    }
}