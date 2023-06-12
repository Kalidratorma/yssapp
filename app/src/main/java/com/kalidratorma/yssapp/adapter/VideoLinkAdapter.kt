package com.kalidratorma.yssapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.R
import com.kalidratorma.yssapp.adapter.VideoLinkAdapter.VideoLinkViewHolder
import com.kalidratorma.yssapp.model.VideoLink
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoLinkAdapter(private val videoLinkList: List<VideoLink>) : RecyclerView.Adapter<VideoLinkViewHolder>() {
    var onItemClick: ((VideoLink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoLinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.video_link_item, parent, false)
        return VideoLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoLinkViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return videoLinkList.size
    }

    inner class VideoLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoLinkItemWebView: YouTubePlayerView

        init {
            videoLinkItemWebView = itemView.findViewById(R.id.youTubePlayerView)

//            itemView.setOnClickListener {
//                onItemClick?.invoke(videoLinkList[adapterPosition])
//            }

            videoLinkItemWebView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = videoLinkList[adapterPosition].url!!
                    youTubePlayer.loadVideo(videoId, 0f)
                    if(adapterPosition!= 0) {
                        youTubePlayer.pause()
                    }
                }
            })
        }
    }
}