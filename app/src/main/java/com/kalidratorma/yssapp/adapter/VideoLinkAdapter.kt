package com.kalidratorma.yssapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.R
import com.kalidratorma.yssapp.adapter.VideoLinkAdapter.VideoLinkViewHolder
import com.kalidratorma.yssapp.model.ContentFile
import com.kalidratorma.yssapp.service.RetrofitHelper


class VideoLinkAdapter(private val videoContentFileList: List<ContentFile>) : RecyclerView.Adapter<VideoLinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoLinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.video_link_item, parent, false)
        return VideoLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoLinkViewHolder, position: Int) {
        holder.videoLinkItemWebView.setVideoURI(
            Uri.parse(RetrofitHelper.getInstance().baseUrl().toString() +
                    "file/" + videoContentFileList[position].name!!))
        holder.videoLinkItemWebView.start()
        holder.videoLinkItemWebView.pause()
    }

    override fun getItemCount(): Int {
        return videoContentFileList.size
    }

    inner class VideoLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoLinkItemWebView: VideoView

        init {
            videoLinkItemWebView = itemView.findViewById(R.id.youTubePlayerView)
            videoLinkItemWebView.setMediaController(MediaController(itemView.context))
        }
    }
}