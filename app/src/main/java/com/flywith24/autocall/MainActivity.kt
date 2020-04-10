package com.flywith24.autocall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var mSelectedNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE
            )
            ActivityCompat.requestPermissions(this@MainActivity, permissions, 100)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mSelectedNumber = parent?.getItemAtPosition(position) as String
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String?) {
        Handler().postDelayed({ call() }, 2000)
    }

    fun call(view: View) {
        call()
    }

    private fun call() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$mSelectedNumber")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE
            )
            ActivityCompat.requestPermissions(this@MainActivity, permissions, 100)
            return
        }

        startActivity(intent)
    }
}
