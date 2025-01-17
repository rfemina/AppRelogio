package com.applogin.apprelogiohome

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.applogin.apprelogiohome.databinding.ActivityAppRelogioBinding

@Suppress("DEPRECATION")
class AppRelogio : AppCompatActivity() {

    private lateinit var binding: ActivityAppRelogioBinding
    private var isChecked = true

    private val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (intent != null) {
                val nivel: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                binding.textNivelBateria.text = "$nivel%"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppRelogioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(bateriaReceiver, intentFilter)

        binding.checkNivelBateria.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.textNivelBateria.visibility = View.GONE
            } else {
                isChecked = true
                binding.textNivelBateria.visibility = View.VISIBLE
            }
            binding.checkNivelBateria.isChecked = isChecked
        }

        binding.layoutMenu.animate().translationY(500F)
        binding.imageViewPreferencias.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }
        binding.imageViewFechar.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat())
                .setDuration(
                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                )
        }

    }
}