package shop.tv.rsys.com.tvapplication.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

import shop.tv.rsys.com.tvapplication.common.BaseUrl;

/**
 * Created by akash.sharma on 12/14/2017.
 */
@Root(name = "BTAResponse", strict = false)
public class BtaResponseModel {

    @ElementList(name = "Movie", inline = true)
    @Path("Movies")
    private List<Movie> Movie;

    public List<BtaResponseModel.Movie> getMovie() {
        return Movie;
    }

    public void setMovie(List<BtaResponseModel.Movie> movie) {
        Movie = movie;
    }

    @Root(name = "Movie", strict = false)
    public static class Movie {
        @Element(name = "Subtype")
        private String SubType;
        @Element(name = "ID")
        private String movieId;
        @Element(name = "Title")
        private String title;
        @Element(name = "longDescription")
        private String longDescription;
        @Element(name = "JPEG")
        private String posterImage;
        @Element(name = "SortName")
        private String sortName;
        @Element(name = "ReleaseYear")
        private String releaseYear;

        @ElementList(name = "RentalItems", inline = true, required = false)
        private List<RentalItems> rentalItems;

        public List<RentalItems> getRentalItems() {
            return rentalItems;
        }

        public void setRentalItems(List<RentalItems> rentalItems) {
            this.rentalItems = rentalItems;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }


        public String getReleaseYear() {
            return releaseYear;
        }

        public void setReleaseYear(String releaseYear) {
            this.releaseYear = releaseYear;
        }

        public String getSubType() {
            return SubType;
        }

        public void setSubType(String subType) {
            SubType = subType;
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

        public String getLongDescription() {
            return longDescription;
        }

        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public String getPosterImage() {
            return BaseUrl.btaImagePathUrl+ posterImage;
        }

        public void setPosterImage(String posterImage) {
            this.posterImage = posterImage;
        }
    }

    @Root(name = "RentalItems", strict = false)
    public static class RentalItems implements Serializable {
        @Element(name = "RentalType")
        private String rentalType;

        @Element(name = "RentalTRC", required = false)
        private String rentalTRC;

        @Element(name = "RentalItem", required = false)
        private RentalItem rentalItem;

        public String getRentalType() {
            return rentalType;
        }

        public void setRentalType(String rentalType) {
            this.rentalType = rentalType;
        }

        public String getRentalTRC() {
            return rentalTRC;
        }

        public void setRentalTRC(String rentalTRC) {
            this.rentalTRC = rentalTRC;
        }

        public RentalItem getRentalItems() {
            return rentalItem;
        }

        public void setRentalItems(RentalItem rentalItems) {
            this.rentalItem = rentalItems;
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


