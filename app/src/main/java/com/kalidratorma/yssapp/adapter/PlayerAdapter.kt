package com.kalidratorma.yssapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.MainActivity
import com.kalidratorma.yssapp.R
import com.kalidratorma.yssapp.adapter.PlayerAdapter.PlayerViewHolder
import com.kalidratorma.yssapp.model.Player

class PlayerAdapter(private val playerList: List<Player>) : RecyclerView.Adapter<PlayerViewHolder>() {
    var onItemClick: ((Player) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.playerItemTextView.text = playerList[position].name
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playerItemTextView: TextView

        init {
            playerItemTextView = itemView.findViewById(R.id.playerItemTextView)

            itemView.setOnClickListener {
                onItemClick?.invoke(playerList[adapterPosition])
            }
        }
    }
}