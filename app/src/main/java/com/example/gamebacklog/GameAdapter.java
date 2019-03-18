package com.example.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    List<Game> mGameList;

    public GameAdapter (List<Game> mGameList) {
        this.mGameList = mGameList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_gamerow, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Game game = mGameList.get(i);
        viewHolder.gameTitleView.setText(game.getGameTitle());
        viewHolder.platformView.setText(game.getPlatform());
        viewHolder.statusView.setText(game.getStatus());
        viewHolder.dateView.setText(game.getDate());

    }

    @Override
    public int getItemCount() {
        return mGameList.size();
    }

    public void swapList (List<Game> newList) {
        mGameList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleView;
        TextView platformView;
        TextView statusView;
        TextView dateView;
        CardView cardViewGame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameTitleView = itemView.findViewById(R.id.gameTitleView);
            platformView = itemView.findViewById(R.id.platformView);
            statusView = itemView.findViewById(R.id.statusView);
            dateView = itemView.findViewById(R.id.dateView);

            cardViewGame = itemView.findViewById(R.id.gameCardView);
        }
    }
}
