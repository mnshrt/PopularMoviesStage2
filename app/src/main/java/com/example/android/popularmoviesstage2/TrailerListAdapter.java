package com.example.android.popularmoviesstage2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.pojo.Trailer;

import java.util.List;

/**
 * Created by emkayx on 06-11-2017.
 */

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerListViewHolder> {

    List<Trailer> trailerList;
    final private TrailerListItemClickListener trailerListItemClickListener;
    int numberOfListItems;

public interface TrailerListItemClickListener{
    void onListItemClick(int clickedItemIndex);
}
    public TrailerListAdapter(int numberOfListItems, List<Trailer> trailerList,TrailerListItemClickListener trailerListItemClickListener)
    {
        this.numberOfListItems = numberOfListItems;
      this.trailerListItemClickListener= trailerListItemClickListener;

        this.trailerList= trailerList;


    }



    @Override
    public TrailerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item,parent, false);
        TrailerListViewHolder trailerListViewHolder = new TrailerListViewHolder(view);
        return trailerListViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerListViewHolder holder, int position) {
          holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfListItems;
    }

    public class TrailerListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView trailerTv;
        public TrailerListViewHolder(View itemView) {
            super(itemView);
            trailerTv = itemView.findViewById(R.id.trailer_link_tv);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            trailerTv.setText("Trailer " + (position+1));

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            trailerListItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
