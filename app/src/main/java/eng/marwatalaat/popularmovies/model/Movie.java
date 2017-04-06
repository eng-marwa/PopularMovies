
package eng.marwatalaat.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

@SimpleSQLTable(table = "movies", provider = "MoviesProvider")
public class Movie implements Parcelable {
    public Movie() {
    }

    @SimpleSQLColumn("poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SimpleSQLColumn("overview")
    @SerializedName("overview")
    @Expose
    private String overview;
    @SimpleSQLColumn("release_date")
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SimpleSQLColumn("id")
    @SerializedName("id")
    @Expose
    private String id;
    @SimpleSQLColumn("original_title")
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SimpleSQLColumn("original_language")
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SimpleSQLColumn("title")
    @SerializedName("title")
    @Expose
    private String title;
    @SimpleSQLColumn("backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SimpleSQLColumn("popularity")
    @SerializedName("popularity")
    @Expose
    private String popularity;
    @SimpleSQLColumn("vote_count")
    @SerializedName("vote_count")
    @Expose
    private String voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SimpleSQLColumn("vote_average")
    @SerializedName("vote_average")
    @Expose
    private String voteAverage;



    protected Movie(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
        voteAverage = in.readString();

    }




    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    /**
     * 
     * @return
     *     The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * 
     * @param posterPath
     *     The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * 
     * @return
     *     The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * 
     * @param adult
     *     The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     * 
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * 
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     * @return
     *     The genreIds
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     * 
     * @param genreIds
     *     The genre_ids
     */
    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * 
     * @param originalTitle
     *     The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * 
     * @return
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * 
     * @param originalLanguage
     *     The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * 
     * @param backdropPath
     *     The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public String getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    /**
     * 
     * @return
     *     The voteCount
     */
    public String getVoteCount() {
        return voteCount;
    }

    /**
     * 
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * 
     * @return
     *     The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     * 
     * @return
     *     The voteAverage
     */
    public String getVoteAverage() {
        return voteAverage;
    }

    /**
     * 
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(id);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeString(popularity);
        parcel.writeString(voteCount);
        parcel.writeString(voteAverage);
    }

    public Movie(String posterPath, String overview, String id, String title, String voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
    }
}
