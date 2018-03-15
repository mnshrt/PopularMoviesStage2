package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.pojo.Review;

import java.util.List;

/**
 * Created by emkayx on 06-11-2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {
    private Context context;
    private List<Review> reviewList;
    int numberListItems;

    public ReviewListAdapter( int numberListItems,List<Review> reviewList) {
        this.numberListItems=numberListItems;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;


        View view = LayoutInflater.from(context).inflate(layoutIdForListItem,parent,false);
        ReviewListViewHolder reviewListViewHolder = new ReviewListViewHolder(view);
        return reviewListViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewListViewHolder holder, int position) {
      holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberListItems;
    }


    public class ReviewListViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthorTv, reviewContentTv;
        public ReviewListViewHolder(View itemView) {
            super(itemView);
            reviewAuthorTv = itemView.findViewById(R.id.review_author__tv);
            reviewContentTv = itemView.findViewById(R.id.review_content_tv);
        }

        void bind(int listIndex){
            Review currentReview = reviewList.get(listIndex);
            reviewAuthorTv.setText(currentReview.getAuthorName());
            reviewContentTv.setText(currentReview.getReview());

        }


    }
}
