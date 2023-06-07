package com.kalidratorma.yssapp

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.service.ApiService
import com.kalidratorma.yssapp.service.RetrofitHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private var progressDialog: ProgressDialog ?= null

    private var player:Player ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnGet).setOnClickListener {
            getPlayerById()
        }
    }

    private fun getPlayerById(): Player? {
        apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        lifecycleScope.launch {
            showLoading("Загрузка днных с сервера ...")
            val result = apiService.getPlayerById(1)
            if(result.isSuccessful) {
                Log.e("getPlayerById", "getPlayerById success: ${result.body()}")
                player = result.body();
                showMainLayout(player)
            } else {
                Log.e("getPlayerById", "getPlayerById failed: ${result.message()}")
            }
            progressDialog?.dismiss()
        }
        return player
    }

    private fun showMainLayout(player: Player?) {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy г.")

        setContentView(R.layout.main)

        findViewById<Button>(R.id.coachTasksButton).setOnClickListener {
            openCoachTask()
        }

        findViewById<Button>(R.id.coachGradeButton).setOnClickListener {
            openCoachGrade()
        }

        findViewById<Button>(R.id.testsButton).setOnClickListener {
            openTests()
        }

        findViewById<Button>(R.id.statsButton).setOnClickListener {
            openStats()
        }

        findViewById<TextView>(R.id.surnameTextView).text = player?.surname
        findViewById<TextView>(R.id.nameTextView).text = player?.name
        findViewById<TextView>(R.id.birthDateEditTextDate).text =  simpleDateFormat.format(player?.birthDate)
        DownloadImageFromInternet(findViewById<ImageView>(R.id.photoImageView)).execute(player?.photo)
    }
    private fun openCoachTask() {
        setContentView(R.layout.coach_tasks)
        findViewById<Button>(R.id.button).setOnClickListener {
            showMainLayout(player)
        }
    }

    private fun openCoachGrade() {
        setContentView(R.layout.coach_assessment)
    }

    private fun openTests() {
        setContentView(R.layout.testing)
    }

    private fun openStats() {
        setContentView(R.layout.statistics)
    }

    private fun showLoading(msg: String) {
        progressDialog = ProgressDialog.show(this, null, msg, true)
    }



    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(applicationContext, "Загрузка изображения ...",
                Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}