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
import com.kalidratorma.yssapp.model.VideoLink


class VideoLinkAdapter(private val videoLinkList: List<VideoLink>) : RecyclerView.Adapter<VideoLinkViewHolder>() {
    var onItemClick: ((VideoLink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoLinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.video_link_item, parent, false)
        return VideoLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoLinkViewHolder, position: Int) {
        holder.videoLinkItemWebView.setVideoURI(Uri.parse(videoLinkList[position].url!!))
        holder.videoLinkItemWebView.start()
        holder.videoLinkItemWebView.pause()
    }

    override fun getItemCount(): Int {
        return videoLinkList.size
    }

    inner class VideoLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoLinkItemWebView: VideoView

        init {
            videoLinkItemWebView = itemView.findViewById(R.id.youTubePlayerView)
            videoLinkItemWebView.setMediaController(MediaController(itemView.context))

          //  videoLinkItemWebView.start()

//            itemView.setOnClickListener {
//                onItemClick?.invoke(videoLinkList[adapterPosition])
//            }

//            videoLinkItemWebView.(object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    val videoId = videoLinkList[adapterPosition].url!!
//                    youTubePlayer.loadVideo(videoId, 0f)
//                    if(adapterPosition!= 0) {
//                        youTubePlayer.pause()
//                    }
//                }
//            })
        }
    }
}