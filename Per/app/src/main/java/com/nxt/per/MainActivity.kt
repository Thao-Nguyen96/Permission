package com.nxt.per

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nxt.per.databinding.ActivityMainBinding
import java.security.Permission

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)

            //kiểm tra xem đã cấp quyền hay chưa
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(ACTION_VIEW)
                intent.data = Uri.parse("https://www.google.com/search?q=google+d%E1%BB%8Bch&oq=go&aqs=chrome.0.69i59l2j69i60l6.6906j0j7&sourceid=chrome&ie=UTF-8")
                //parse các trang web
                startActivity(intent)
            } else {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_SMS),
                    100)
            }
        }
    }

    //request permission dể cấp quyền
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100){
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(ACTION_VIEW)
                intent.data = Uri.parse("sms:")
                startActivity(intent)
            }else{
                Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}