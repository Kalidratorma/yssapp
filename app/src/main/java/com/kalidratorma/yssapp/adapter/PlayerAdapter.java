package com.kalidratorma.yssapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalidratorma.yssapp.R;
import com.kalidratorma.yssapp.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> playerList;

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.playerItemTextView.setText(playerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {

        TextView playerItemTextView;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            playerItemTextView = itemView.findViewById(R.id.playerItemTextView);
        }
    }
}
