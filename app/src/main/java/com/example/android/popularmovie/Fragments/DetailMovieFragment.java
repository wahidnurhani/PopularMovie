package com.example.android.popularmovie.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovie.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovieFragment extends Fragment {


    public DetailMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_detail_movie, container, false);

        TextView titleTextView = (TextView)rootView.findViewById(R.id.textView_detail_fragment_Title);
        TextView releaseDate = (TextView)rootView.findViewById(R.id.textView_detail_fragment_release_date);
        TextView textViewSynopsis = (TextView)rootView.findViewById(R.id.textView_id_synopsis);
        TextView textViewVote = (TextView)rootView.findViewById(R.id.textView_id_average_rating);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView_id_poster);

        Intent intent =getActivity().getIntent();

        String title = intent.getStringExtra("INTENT_TITLE");
        String release_Date = intent.getStringExtra("INTENT_RELEASE_DATE");
        String synopsis = intent.getStringExtra(Intent.EXTRA_TEXT);
        String posterPath = intent.getStringExtra("Intent_poster");
        String vote = intent.getStringExtra("Intent_average_vote");

        titleTextView.setText(title);
        releaseDate.setText(release_Date);
        textViewSynopsis.setText(synopsis);
        textViewVote.setText(vote);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500/"+posterPath).into(imageView);




        return rootView;
    }

}
