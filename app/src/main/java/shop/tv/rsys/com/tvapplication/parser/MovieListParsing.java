package shop.tv.rsys.com.tvapplication.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import shop.tv.rsys.com.tvapplication.Movie;
import shop.tv.rsys.com.tvapplication.model.BtaResponseModel;
import shop.tv.rsys.com.tvapplication.model.MovieDetailsModel;

/**
 * Created by akash.sharma on 12/15/2017.
 */

public class MovieListParsing {

    public static List<Movie> getMovieList(List<BtaResponseModel.Movie> btaMovieList) {
        List<Movie> movieList = new ArrayList<>();
        Iterator<BtaResponseModel.Movie> iterator = btaMovieList.iterator();
        while (iterator.hasNext()) {
            Movie movie = new Movie();
            BtaResponseModel.Movie btaMovie = iterator.next();
            movie.setTitle(btaMovie.getTitle());
            movie.setCardImageUrl(btaMovie.getPosterImage());
            movie.setStudio(btaMovie.getSortName() + "-" + btaMovie.getReleaseYear());
            movie.setDescription(btaMovie.getLongDescription());
            movie.setRentalItems(btaMovie.getRentalItems().get(0).getRentalItems());
            movieList.add(movie);
        }
        return movieList;
    }


    /*public static Movie getMovieSearchDetails(List<MovieDetailsModel.Movie> movieDetail) {
        Movie movie = new Movie();
        Iterator<MovieDetailsModel.Movie> iterator = movieDetail.iterator();
        while (iterator.hasNext()) {

            MovieDetailsModel.Movie btaMovie = iterator.next();
            movie.setStudio(btaMovie.getLongDescription());
            movie.setTitle(btaMovie.getTitle());
            movie.setCardImageUrl(btaMovie.getPosterImage());

        }
        return movie;
    }*/

    public static List<Movie> getMovieSearchDetails(List<MovieDetailsModel.Movie> movieDetail) {
        List<Movie> movieList = new ArrayList<>();
        Iterator<MovieDetailsModel.Movie> iterator = movieDetail.iterator();
        while (iterator.hasNext()) {
            Movie movie = new Movie();
            MovieDetailsModel.Movie btaMovie = iterator.next();
            movie.setTitle(btaMovie.getTitle());
            movie.setCardImageUrl(btaMovie.getPosterImage());
            movie.setStudio(btaMovie.getSortName() + "-" + btaMovie.getReleaseYear());
            movie.setDescription(btaMovie.getLongDescription());
            movie.setRentalItems(btaMovie.getRentalItem());
            movieList.add(movie);
        }
        return movieList;
    }
}
