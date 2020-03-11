package com.example.admin.videolendingsystem.Video;

/**
 * Created by ADMIN on 23/05/2017.
 */

public class Videoadd {


    private String videoName;
    private String genre;
    private String director;
    private String rating;
    private String releaseDate;
    private String custkey;

    public Videoadd(){

    }

    public String getVideoName() {return videoName;}
    public void setVideoName(String videoName) {this.videoName = videoName;}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    public String getDirector() {return director;}
    public void setDirector(String director) {this.director = director;}

    public String getRating() {return rating;}
    public void setRating(String rating) {this.rating = rating;}

    public String getReleaseDate() {return releaseDate;}
    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}

    public String getVideoKey() {
        return custkey;
    }

    public void setVideoKey(String custkey) {
        this.custkey = custkey;
    }

}
