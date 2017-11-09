package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.pojo.Trailer;

import java.util.List;

/**
 * Created by emkayx on 06-11-2017.
 */

public class TrailerListAdapter extends ArrayAdapter<Trailer> {
    Context context;
    List<Trailer> trailerList;


    public TrailerListAdapter(Context context, List<Trailer> trailerList)
    {
        super(context, 0, trailerList);
        this.context= context;
        this.trailerList= trailerList;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView== null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.trailer_list_item,parent,false);
        }
        Trailer currentTrailer = trailerList.get(position);

        // get the textview id
        TextView trailerLinkTextView = listItemView.findViewById(R.id.trailer_link_tv);

        trailerLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        String trailerLink ="<a href=\"https://www.youtube.com/watch?v=" +currentTrailer.getVideoId()+"\">Trailer "+ (position+1)+"</a>";

                //http://api.themoviedb.org/3/movie/346364/videos?api_key=e58cd6903218bfa3ff6cfe1c12977fc9
        trailerLinkTextView.append(trailerLink+"\n");

        return listItemView;


    }
}
