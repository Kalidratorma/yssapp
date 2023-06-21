@file:Suppress("DEPRECATION")

package com.kalidratorma.yssapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

@Suppress("DEPRECATION")
class DownloadImageFromInternet(context: Context, private var imageView: ImageView) :
    AsyncTask<String, Void, Bitmap?>() {
    init {
        Toast.makeText(
            context, "Загрузка изображения ...", Toast.LENGTH_SHORT
        ).show()
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg urls: String): Bitmap? {
        val imageURL = urls[0]
        var image: Bitmap? = null
        try {
            val `in` = java.net.URL(imageURL).openStream()
            image = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }
        return image
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }
}