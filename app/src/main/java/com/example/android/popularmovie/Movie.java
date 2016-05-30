package com.example.android.popularmovie;

/**
 * Created by AkhmadMurphy on 29/05/16.
 */
public class Movie {

    String title;
    String releaseDate;
    String imageSource ;
    double voteAverage ;
    String plotSynopsis;


    public Movie( String title, String releaseDate, String imageSource, double voteAverage, String plotSynopsis) {
        this.title = title;
        this.releaseDate= releaseDate;
        this.imageSource = imageSource;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageSource() {
        return imageSource;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
