package com.example.android.popularmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmovie.Movie;
import com.example.android.popularmovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by AkhmadMurphy on 29/05/16.
 */

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private  static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    public MovieListAdapter(Context context, int resource, int imageViewResourceId) {
        super(context, resource, imageViewResourceId);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.image_item_layout, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_image);

        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500/"+movie.getImageSource()).into(iconView);


        return convertView;
    }


}
