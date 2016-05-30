package com.example.android.popularmovie.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovie.Activities.AppPreferences;
import com.example.android.popularmovie.Activities.DetailActivity;
import com.example.android.popularmovie.Adapter.MovieListAdapter;
import com.example.android.popularmovie.Movie;
import com.example.android.popularmovie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    private MovieListAdapter movieListAdapter;


    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id ==R.id.menu_settings){
            Intent intent = new Intent(getActivity(), AppPreferences.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovie(){

        FetchMovie movieTask = new FetchMovie();
        String sort = PreferenceManager.getDefaultSharedPreferences(getActivity()).
                getString(getString(R.string.pref_sort_type_key),getString(R.string.pref_sort_default));
        movieTask.execute(sort);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        GridView gridViewMovieList = (GridView)rootView.findViewById(R.id.gridView_Mmovie_list);

        movieListAdapter = new MovieListAdapter(getActivity(), R.layout.image_item_layout, R.id.movie_image);


        gridViewMovieList.setAdapter(movieListAdapter);

        gridViewMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = movieListAdapter.getItem(position).getTitle();
                String releaseDate = movieListAdapter.getItem(position).getReleaseDate();
                String synopsis = movieListAdapter.getItem(position).getPlotSynopsis();
                String posterPath = movieListAdapter.getItem(position).getImageSource();
                String voteAverage = movieListAdapter.getItem(position).getVoteAverage()+"";


                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("INTENT_TITLE",title);
                i.putExtra("INTENT_RELEASE_DATE",releaseDate);
                i.putExtra(Intent.EXTRA_TEXT, synopsis);
                i.putExtra("Intent_poster",posterPath);
                i.putExtra("Intent_average_vote",voteAverage+"");
                startActivity(i);
            }
        });

        return rootView;
    }

    private class FetchMovie extends AsyncTask<String, Void, Movie[]> {


        @Override
        protected Movie[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;
            String apiKey = "";
            String param = params[0];

            try {
                String baseUrl = "https://api.themoviedb.org/3/movie/"+param+"?api_key="+apiKey;

                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line ;

                while ((line=reader.readLine())!= null){
                    buffer.append(line+"\n");
                }


                movieJsonStr = buffer.toString();



            } catch (IOException e){
                return null;
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException{


            JSONObject moviesJson = new JSONObject(movieJsonStr);
            JSONArray movieJsonArray = moviesJson.getJSONArray("results");

            Movie[] result = new Movie[movieJsonArray.length()];

            for (int i =0; i< movieJsonArray.length(); i++){

                String title;
                String releaseDate;
                String synopsis;
                double averageVote;
                String posterPath;

                JSONObject oneMovie = movieJsonArray.getJSONObject(i);

                title = oneMovie.getString("original_title");
                releaseDate = oneMovie.getString("release_date");
                posterPath = oneMovie.getString("poster_path");
                averageVote = oneMovie.getDouble("vote_average");
                synopsis = oneMovie.getString("overview");

                result[i] = new Movie(title,releaseDate,posterPath,averageVote,synopsis);

            }
            return result;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {

            if (movies != null) {
                movieListAdapter.clear();
                for (Movie tmp : movies){
                    movieListAdapter.add(tmp);
                }
            }
        }



    }

}
