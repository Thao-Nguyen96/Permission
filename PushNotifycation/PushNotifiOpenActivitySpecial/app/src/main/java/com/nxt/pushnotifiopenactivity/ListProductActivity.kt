package com.nxt.pushnotifiopenactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nxt.pushnotifiopenactivity.databinding.ActivityListProductBinding

class ListProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title= "ListProduct Activity"

        binding.btnGoToDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}