package com.example.handybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.handybook.databinding.ActivityMainBinding
import com.example.handybook.ui.SplashScreenFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.main, SplashScreenFragment())
            .commit()
    }
}