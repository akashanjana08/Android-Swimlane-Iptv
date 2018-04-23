package shop.tv.rsys.com.tvapplication.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

import shop.tv.rsys.com.tvapplication.common.BaseUrl;

/**
 * Created by akash.sharma on 2/12/2018.
 */
@Root(name="MOVIELIST" ,strict=false)
public class MovieDetailsModel {

    @Attribute(name = "totalMovies")
    private int totalMovie;


    @ElementList(name="Movie", inline=true , required = false)
    private List<Movie> Movie;


    public List<MovieDetailsModel.Movie> getMovie() {
        return Movie;
    }

    public void setMovie(List<MovieDetailsModel.Movie> movie) {
        Movie = movie;
    }

    public int getTotalMovie() {
        return totalMovie;
    }

    public void setTotalMovie(int totalMovie) {
        this.totalMovie = totalMovie;
    }

    @Root(name="Movie" ,strict=false)
    public static class Movie
    {
        @Element(name = "ID", required = false)
        private String movieId;

        @Element(name = "Title", required = false)
        private String title;

        @Element(name = "SortName", required = false)
        private String sortName;

        @Element(name="longDescription", required = false)
        private String longDescription;

        @Element(name="JPEG", required = false)
        private String posterImage;

        @Element(name = "ReleaseYear", required = false)
        private String releaseYear;

        @Element(name = "RentalItem", required = false)
        @Path("RentalItems")
        private BtaResponseModel.RentalItem rentalItem;

        public String getPosterImage() {
               return BaseUrl.btaImagePathUrl+posterImage;
        }

        public void setPosterImage(String posterImage) {
            this.posterImage = posterImage;
        }

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public String getReleaseYear() {
            return releaseYear;
        }

        public void setReleaseYear(String releaseYear) {
            this.releaseYear = releaseYear;
        }

        public BtaResponseModel.RentalItem getRentalItem() {
            return rentalItem;
        }

        public void setRentalItem(BtaResponseModel.RentalItem rentalItem) {
            this.rentalItem = rentalItem;
        }
    }


    @Root(name = "RentalItem", strict = false)
    public static class RentalItem implements Serializable {
        @Element(name = "RentalPeriod", required = false)
        private String rentalPeriod;

        @Element(name = "RentalPeriodStartDate", required = false)
        private String rentalPeriodStartDate;

        @Element(name = "RentalPeriodEndDate", required = false)
        private String rentalPeriodEndDate;

        @Element(name = "RentalPeriodHours", required = false)
        private String rentalPeriodHours;

        @Element(name = "RentalPeriodDuration", required = false)
        private String rentalPeriodDuration;

        @Element(name = "RentalCost", required = false)
        private String rentalCost;

        public String getRentalPeriod() {
            return rentalPeriod;
        }

        public void setRentalPeriod(String rentalPeriod) {
            this.rentalPeriod = rentalPeriod;
        }

        public String getRentalPeriodStartDate() {
            return rentalPeriodStartDate;
        }

        public void setRentalPeriodStartDate(String rentalPeriodStartDate) {
            this.rentalPeriodStartDate = rentalPeriodStartDate;
        }

        public String getRentalPeriodEndDate() {
            return rentalPeriodEndDate;
        }

        public void setRentalPeriodEndDate(String rentalPeriodEndDate) {
            this.rentalPeriodEndDate = rentalPeriodEndDate;
        }

        public String getRentalPeriodHours() {
            return rentalPeriodHours;
        }

        public void setRentalPeriodHours(String rentalPeriodHours) {
            this.rentalPeriodHours = rentalPeriodHours;
        }

        public String getRentalPeriodDuration() {
            return rentalPeriodDuration;
        }

        public void setRentalPeriodDuration(String rentalPeriodDuration) {
            this.rentalPeriodDuration = rentalPeriodDuration;
        }

        public String getRentalCost() {
            return rentalCost;
        }

        public void setRentalCost(String rentalCost) {
            this.rentalCost = rentalCost;
        }
    }

}
