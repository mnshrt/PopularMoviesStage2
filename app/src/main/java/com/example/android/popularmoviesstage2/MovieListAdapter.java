package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emkayx on 30-10-2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private List<Movie> movieList;
    final private PosterClickListener mPosterClickListener;
Context context;

    // interface to respond to clicks
    public interface PosterClickListener{
        void onMoviePosterClick(int clickedItemIndex);
    }

    public MovieListAdapter(List<Movie> movieList, PosterClickListener mPosterClickListener) {
        this.movieList = movieList;
        this.mPosterClickListener = mPosterClickListener;

    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         context = parent.getContext();
        int layoutIdForListItem = R.layout.single_movie_item;
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(context).inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieListViewHolder holder, int position) {

        Movie currentMovie = movieList.get(position);
        String moviePosterUrl = currentMovie.getMoviePosterPath();
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+ moviePosterUrl).into(holder.posterImageView);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public void setMovieList(List<Movie> movies){
        movieList = movies;
        this.notifyDataSetChanged();
    }

    public void clear(){
        int size= getItemCount();
        movieList.clear();
        notifyItemRangeRemoved(0,size);

    }
    // view holder inner class

    public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView posterImageView;
        public MovieListViewHolder(View itemView) {
            super(itemView);
            posterImageView= itemView.findViewById(R.id.poster_imsgeview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition= getAdapterPosition();
            mPosterClickListener.onMoviePosterClick(clickedPosition);
        }
    }

}
